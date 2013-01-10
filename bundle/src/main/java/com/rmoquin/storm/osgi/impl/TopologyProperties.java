package com.rmoquin.storm.osgi.impl;

import backtype.storm.Config;
import com.rmoquin.storm.osgi.topology.ITopologyProperties;
import java.util.HashMap;
import java.util.Map;

/**
 * Properties for configuring a topology.
 *
 * @org.apache.xbean.XBean element="properties" description="Properties for configuring a topology."
 *
 * @author rmoquin
 */
public class TopologyProperties implements ITopologyProperties {

  private boolean debug = false;
  private Integer workers = null;
  private Map<String, Object> configuration = new HashMap<String, Object>();

  /**
   * @return the debug
   */
  @Override
  public boolean isDebug() {
    return debug;
  }

  /**
   * When set to true, Storm will log every message that's emitted.
   *
   * @param debug the debug to set
   */
  @Override
  public void setDebug(boolean debug) {
    this.debug = debug;
    this.configuration.put(Config.TOPOLOGY_DEBUG, this.debug);
  }

  /**
   * @return the configuration
   */
  @Override
  public Map<String, Object> getConfiguration() {
    return configuration;
  }

  /**
   * How many processes should be spawned around the cluster to execute this topology. Each process will execute some
   * number of tasks as threads within them. This parameter should be used in conjunction with the parallelism hints on
   * each component in the topology to tune the performance of a topology.
   *
   * @return the workers
   */
  @Override
  public Integer getWorkers() {
    return workers;
  }

  /**
   * How many processes should be spawned around the cluster to execute this topology. Each process will execute some
   * number of tasks as threads within them. This parameter should be used in conjunction with the parallelism hints on
   * each component in the topology to tune the performance of a topology.
   *
   * @param workers the numWorkers to set
   */
  @Override
  public void setWorkers(Integer workers) {
    this.workers = workers;
    if (workers != null) {
      this.configuration.put(Config.TOPOLOGY_WORKERS, this.workers);
    }
  }
}
