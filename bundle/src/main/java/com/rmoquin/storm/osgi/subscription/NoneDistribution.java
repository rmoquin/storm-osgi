package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * This grouping specifies that you don't care how the stream is grouped.
 * Currently, none groupings are equivalent to shuffle groupings. Eventually
 * though, Storm will push down bolts with none groupings to execute in the same
 * thread as the bolt or spout they subscribe from (when possible).
 *
 * @org.apache.xbean.XBean element="none" description="This grouping
 * specifies that you don't care how the stream is grouped. Currently, none
 * groupings are equivalent to shuffle groupings. Eventually though, Storm will
 * push down bolts with none groupings to execute in the same thread as the bolt
 * or spout they subscribe from (when possible)."
 *
 * @author rmoquin
 */
public class NoneDistribution implements DistributionPolicy {
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.noneGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
