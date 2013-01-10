package com.rmoquin.storm.osgi.topology;

import com.rmoquin.storm.osgi.bolt.BoltDefinition;
import com.rmoquin.storm.osgi.spout.SpoutDefinition;
import java.util.List;

/**
 * Represents a storm topology.
 * 
 * @org.apache.xbean.XBean element="topology" description="Represents the definition of a Storm topology"
 *
 * @author rmoquin
 */
public class TopologyDefinition implements ITopologyDefinition {
  private String name;
  private ITopologyProperties configuration;
  private List<BoltDefinition> bolts;
  private List<SpoutDefinition> spouts;

  /**
   * @return the bolts
   */
  @Override
  public List<BoltDefinition> getBolts() {
    return bolts;
  }

  /**
   * @param bolts the bolts to set
   */
  @Override
  public void setBolts(List<BoltDefinition> bolts) {
    this.bolts = bolts;
  }

  /**
   * @return the spouts
   */
  @Override
  public List<SpoutDefinition> getSpouts() {
    return spouts;
  }

  /**
   * @param spouts the spouts to set
   */
  @Override
  public void setSpouts(List<SpoutDefinition> spouts) {
    this.spouts = spouts;
  }

  /**
   * @return the name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the configuration
   */
  @Override
  public ITopologyProperties getConfiguration() {
    return configuration;
  }

  /**
   * @param configuration the configuration to set
   */
  @Override
  public void setConfiguration(ITopologyProperties configuration) {
    this.configuration = configuration;
  }
}
