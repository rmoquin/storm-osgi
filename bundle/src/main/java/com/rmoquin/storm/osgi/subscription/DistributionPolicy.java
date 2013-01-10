package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;
import java.io.Serializable;

/**
 *
 * @author rmoquin
 */
public interface DistributionPolicy extends Serializable {
  public void setup(BoltDeclarer declarer, Subscription subscription);
}
