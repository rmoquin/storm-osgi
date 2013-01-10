package com.rmoquin.storm.osgi.topology;

import java.util.Map;

/**
 *
 * @author rmoquin
 */
public interface ITopologyProperties {

  /**
   * @return the configuration
   */
  Map<String, Object> getConfiguration();

  /**
   * @return the debug
   */
  boolean isDebug();

  /**
   * When set to true, Storm will log every message that's emitted.
   *
   * @param debug the debug to set
   */
  void setDebug(boolean debug);

  /**
   * How many processes should be spawned around the cluster to execute this topology. Each process will execute some number of tasks as threads within them. This parameter should be used in conjunction with the parallelism hints on each component in the topology to tune the performance of a topology.
   * 
   * @return the workers
   */
  Integer getWorkers();

  /**
   * How many processes should be spawned around the cluster to execute this topology. Each process will execute some number of tasks as threads within them. This parameter should be used in conjunction with the parallelism hints on each component in the topology to tune the performance of a topology.
   * 
   * @param workers the numWorkers to set
   */
  void setWorkers(Integer workers);
  
}
