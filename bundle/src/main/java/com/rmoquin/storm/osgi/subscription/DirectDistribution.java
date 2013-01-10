package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * This is a special kind of grouping. A stream grouped this way means that the
 * producer of the tuple decides which task of the consumer will receive this
 * tuple. Direct groupings can only be declared on streams that have been
 * declared as direct streams. Tuples emitted to a direct stream must be emitted
 * using one of the emitDirect methods. A bolt can get the task ids of its
 * consumers by either using the provided TopologyContext or by keeping track of
 * the output of the emit method in OutputCollector (which returns the task ids
 * that the tuple was sent to).
 *
 * @org.apache.xbean.XBean element="none" description="This is a special kind of
 * grouping. A stream grouped this way means that the producer of the tuple
 * decides which task of the consumer will receive this tuple. Direct groupings
 * can only be declared on streams that have been declared as direct streams.
 * Tuples emitted to a direct stream must be emitted using one of the emitDirect
 * methods. A bolt can get the task ids of its consumers by either using the
 * provided TopologyContext or by keeping track of the output of the emit method
 * in OutputCollector (which returns the task ids that the tuple was sent to).
 *
 * @author rmoquin
 */
public class DirectDistribution implements DistributionPolicy {

  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.directGrouping(subscription.getTo(), subscription.getStream().getId());
  }
}
