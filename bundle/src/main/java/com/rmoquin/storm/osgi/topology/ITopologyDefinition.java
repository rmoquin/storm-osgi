package com.rmoquin.storm.osgi.topology;

import com.rmoquin.storm.osgi.bolt.BoltDefinition;
import com.rmoquin.storm.osgi.spout.SpoutDefinition;
import java.util.List;

/**
 *
 * @author rmoquin
 */
public interface ITopologyDefinition {

  /**
   * @return the bolts
   */
  List<BoltDefinition> getBolts();

  /**
   * @return the spouts
   */
  List<SpoutDefinition> getSpouts();

  /**
   * @param bolts the bolts to set
   */
  void setBolts(List<BoltDefinition> bolts);

  /**
   * @param spouts the spouts to set
   */
  void setSpouts(List<SpoutDefinition> spouts);

  /**
   * The configuration for this topology.
   * 
   * @return the configuration
   */
  ITopologyProperties getConfiguration();

  /**
   * @return the name
   */
  String getName();

  /**
   * The configuration for this topology.
   * 
   * @param config the configuration to set
   */
  void setConfiguration(ITopologyProperties properties);

  /**
   * @param name the name to set
   */
  void setName(String name);
  
}
