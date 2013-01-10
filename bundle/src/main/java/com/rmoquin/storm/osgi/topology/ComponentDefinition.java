package com.rmoquin.storm.osgi.topology;

import backtype.storm.Config;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import com.rmoquin.storm.osgi.tuple.TupleStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rmoquin
 */
public class ComponentDefinition implements ITopologyComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComponentDefinition.class);
  protected String name;
  private Integer parallelismHint;
  private String[] schema;
  private TupleStream[] streams;
  protected Map<String, Object> configuration = new HashMap<String, Object>();

  @PostConstruct
  public void initDefinition() {
    if ((streams == null) && (schema != null)) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Component, " + this.name + ", has a default tuple schema, creating a default stream for it.");
      }
      TupleStream defaultStream = new TupleStream();
      defaultStream.setId(Utils.DEFAULT_STREAM_ID);
      defaultStream.setSchema(schema);
      streams = new TupleStream[]{defaultStream};
    }
  }

  @PreDestroy
  public void destroyDefinition() {
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    for (TupleStream stream : streams) {
      declarer.declareStream(stream.getId(), new Fields(stream.getSchema()));
    }
  }

  /**
   * The name of this component.
   *
   * @return the name of this component.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * The name of this component.
   *
   * @param name the name of this component.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * The initial number of executors (threads) this component should have.
   *
   * @return the parallelismHint
   */
  @Override
  public Integer getParallelismHint() {
    return parallelismHint;
  }

  /**
   * The initial number of executors (threads) this component should have.
   *
   * @param parallelismHint the initial number of threads.
   */
  @Override
  public void setParallelismHint(Integer parallelismHint) {
    this.parallelismHint = parallelismHint;
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return this.configuration;
  }

  /**
   * The set of fields output by this component.
   *
   * @return the schema
   */
  public String[] getSchema() {
    return schema;
  }

  /**
   * Sets the schema for this component.
   *
   * @param schema the schema to set
   */
  public void setSchema(String[] schema) {
    this.schema = schema;
  }

  /**
   * @return the streams
   */
  public TupleStream[] getStreams() {
    return streams;
  }

  /**
   * When set to true, Storm will log every message that's emitted.
   */
  public void setDebug(boolean debug) {
    this.configuration.put(Config.TOPOLOGY_DEBUG, debug);
  }

  /**
   * The maximum parallelism allowed for a component in this topology. This configuration is typically used in testing
   * to limit the number of threads spawned in local mode.
   */
  public void setMaxTaskParallelism(int val) {
    this.configuration.put(Config.TOPOLOGY_MAX_TASK_PARALLELISM, val);
  }

  /**
   * The maximum number of tuples that can be pending on a spout task at any given time. This config applies to
   * individual tasks, not to spouts or topologies as a whole.
   *
   * A pending tuple is one that has been emitted from a spout but has not been acked or failed yet. Note that this
   * config parameter has no effect for unreliable spouts that don't tag their tuples with a message id.
   */
  public void setMaxSpoutPending(int val) {
    this.configuration.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, val);
  }

  /**
   * How many instances to create for a spout/bolt. A task runs on a thread with zero or more other tasks for the same
   * spout/bolt. The number of tasks for a spout/bolt is always the same throughout the lifetime of a topology, but the
   * number of executors (threads) for a spout/bolt can change over time. This allows a topology to scale to more or
   * less resources without redeploying the topology or violating the constraints of Storm (such as a fields grouping
   * guaranteeing that the same value goes to the same task).
   */
  public void setNumTasks(int val) {
    this.configuration.put(Config.TOPOLOGY_TASKS, val);
  }
}
