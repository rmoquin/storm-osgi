package com.rmoquin.storm.osgi.impl;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.TopologyBuilder;
import clojure.osgi.IClojureLoader;
import com.rmoquin.storm.osgi.bolt.BoltDefinition;
import com.rmoquin.storm.osgi.spout.SpoutDefinition;
import com.rmoquin.storm.osgi.subscription.DistributionPolicy;
import com.rmoquin.storm.osgi.topology.ITopologyDefinition;
import com.rmoquin.storm.osgi.topology.ITopologyProperties;
import com.rmoquin.storm.osgi.topology.Subscription;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rmoquin
 */
public class TopologyManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TopologyManager.class);
  private IClojureLoader clojureLoader;
  private ConcurrentMap<String, ITopologyDefinition> topologyMap = new ConcurrentHashMap<String, ITopologyDefinition>();
  private LocalCluster cluster;

  public void init() {
  }

  public void destroy() {
    if (cluster != null) {
      try {
        cluster.shutdown();
      } catch (Exception ex) {
        LOGGER.warn("Error attempting to shutdown cluster.", ex);
      }
    }
    topologyMap.clear();
  }

  public void configRegistered(String pid, Dictionary<String, Object> properties) {
    LOGGER.error("Received properties for pid: " + pid + " " + properties);
  }
  
  public void configDeregistered(String pid, Dictionary properties) {
    LOGGER.error("Received removed properties for pid: " + pid);
  }
  
  public void onBindService(ITopologyDefinition definition) {
    try {
      this.submitTopology(definition);
    } catch (Exception ex) {
      LOGGER.error("Error binding topology service.", ex);
    }
  }

  public void onUnbindService(ITopologyDefinition definition) {
    if (definition == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Null topology definition was provided for unbind service, ignoring.");
      }
      return;
    }
    shutdownTopology(definition);
  }

  public void submitTopology(ITopologyDefinition definition) throws Exception {
    ITopologyDefinition previousDefinition = this.topologyMap.putIfAbsent(definition.getName(), definition);
    if (previousDefinition != null) {
      throw new IllegalStateException("A topology with the name, " + definition.getName() + " has already been deployed, request for deployment will be ignored.");
    } else {
      TopologyBuilder builder = new TopologyBuilder();
      try {
        //Do this here, if done earlier, it seems to cause stupid Clojure to lockup.
        if (this.cluster == null) {
          cluster = (LocalCluster) this.clojureLoader.createInstance(this.getClass().getClassLoader(), "backtype.storm.LocalCluster");
        }
        List<SpoutDefinition> spouts = definition.getSpouts();
        for (Iterator<SpoutDefinition> it = spouts.iterator(); it.hasNext();) {
          SpoutDefinition spoutDef = it.next();
          builder.setSpout(spoutDef.getName(), spoutDef, spoutDef.getParallelismHint());
        }
        List<BoltDefinition> bolts = definition.getBolts();
        for (int i = 0; i < bolts.size(); i++) {
          BoltDefinition boltDef = bolts.get(i);
          BoltDeclarer declarer = builder.setBolt(boltDef.getName(), boltDef, boltDef.getParallelismHint());
          Subscription subscription = boltDef.getSubscription();
          if (subscription.getStream() == null) {
            String to = subscription.getTo();
            for (int j = 0; j < spouts.size(); j++) {
              SpoutDefinition sd = spouts.get(j);
              if (to.equals(sd.getName())) {
                subscription.setStream(sd.getStreams()[0]);
                break;
              }
            }
            if (subscription.getStream() == null) {
              for (int j = 0; j < bolts.size(); j++) {
                BoltDefinition bd = bolts.get(j);
                if (to.equals(bd.getName())) {
                  subscription.setStream(bd.getStreams()[0]);
                  break;
                }
              }
            }
          }
          DistributionPolicy distribution = subscription.getDistribution();
          distribution.setup(declarer, subscription);
        }
      } catch (Exception ex) {
        this.topologyMap.remove(definition.getName());
        throw new IllegalStateException("Error building topology, " + definition.getName() + ", due to an error, topology won't be deployed.", ex);
      }

      try {
        Config conf = new Config();
        ITopologyProperties properties = definition.getConfiguration();
        if(properties != null) {
          conf.putAll(properties.getConfiguration());
        }
        cluster.submitTopology(definition.getName(), conf, builder.createTopology());
      } catch (Exception ex) {
        this.shutdownTopology(definition);
        LOGGER.warn("Not deploying topology, " + definition.getName() + ", due to an error.", ex);
      }
    }
    //}
  }

  /**
   * @return the clojureLoader
   */
  public IClojureLoader getClojureLoader() {
    return clojureLoader;
  }

  /**
   * @param clojureLoader the clojureLoader to set
   */
  public void setClojureLoader(IClojureLoader clojureLoader) {
    this.clojureLoader = clojureLoader;
  }

  private void shutdownTopology(final ITopologyDefinition definition) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Topology service, " + definition.getName() + " is being shutdown.");
    }
    this.topologyMap.remove(definition.getName());
    try {
      this.clojureLoader.invoke(this.getClass().getClassLoader(), new Callable<Object>() {
        @Override
        public Object call() throws Exception {
          cluster.killTopology(definition.getName());
          return true;
        }
      });
      if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Topology service, " + definition.getName() + " was shutdown successfully.");
    }
    } catch (Exception ex) {
      LOGGER.debug(definition.getName() + " topology failed during shutdown request (probably not active).", ex);
    }
  }
}
