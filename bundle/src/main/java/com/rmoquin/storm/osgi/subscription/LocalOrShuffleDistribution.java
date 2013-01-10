package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * If the target bolt has one or more tasks in the same worker process, tuples
 * will be shuffled to just those in-process tasks. Otherwise, this acts like a
 * normal shuffle grouping.
 *
 * @org.apache.xbean.XBean element="localOrShuffle" description="If the target bolt
 * has one or more tasks in the same worker process, tuples will be shuffled to
 * just those in-process tasks. Otherwise, this acts like a normal shuffle
 * grouping."
 *
 * @author rmoquin
 */
public class LocalOrShuffleDistribution implements DistributionPolicy {
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.localOrShuffleGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
