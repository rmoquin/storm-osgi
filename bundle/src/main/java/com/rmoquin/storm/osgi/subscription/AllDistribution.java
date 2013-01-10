package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * The stream is replicated across all the bolt's tasks. Use this grouping with care.
 *
 * @org.apache.xbean.XBean element="all" description="The stream is
 * replicated across all the bolt's tasks. Use this grouping with care."
 *
 * @author rmoquin
 */
public class AllDistribution implements DistributionPolicy {
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.allGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
