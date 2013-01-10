package com.rmoquin.storm.osgi.topology;

import backtype.storm.topology.IComponent;

/**
 *
 * @author rmoquin
 */
public interface ITopologyComponent extends IComponent {

  /**
   * @return the name
   */
  String getName();

  /**
   * @param name the name to set
   */
  void setName(String name);

  /**
   * @return the parallelismHint
   */
  Integer getParallelismHint();

  /**
   * @param parallelismHint the parallelismHint to set
   */
  void setParallelismHint(Integer parallelismHint);
}
