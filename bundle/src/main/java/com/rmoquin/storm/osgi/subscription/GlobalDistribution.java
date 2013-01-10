package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * The entire stream goes to a single one of the bolt's tasks. Specifically, it
 * goes to the task with the lowest id.
 *
 * @org.apache.xbean.XBean element="global" description="The entire stream
 * goes to a single one of the bolt's tasks. Specifically, it goes to the task
 * with the lowest id."
 *
 * @author rmoquin
 */
public class GlobalDistribution implements DistributionPolicy {
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.globalGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
