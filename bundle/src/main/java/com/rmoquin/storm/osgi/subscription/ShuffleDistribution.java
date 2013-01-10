package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * A subscription which specifies the distribution tuples from the target
 * component as the criteria for which lister to distribute to.
 *
 * @org.apache.xbean.XBean element="shuffle" description="Tuples are randomly
 * distributed across the bolt's tasks in a way such that each bolt is
 * guaranteed to get an equal number of tuples."
 *
 * @author rmoquin
 */
public class ShuffleDistribution implements DistributionPolicy {
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.shuffleGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
