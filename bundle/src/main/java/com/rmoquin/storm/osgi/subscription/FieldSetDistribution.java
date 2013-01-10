package com.rmoquin.storm.osgi.subscription;

import backtype.storm.topology.BoltDeclarer;
import backtype.storm.tuple.Fields;
import com.rmoquin.storm.osgi.topology.Subscription;

/**
 * The stream is partitioned by the fields specified in the grouping. For
 * example, if the stream is grouped by the "user-id" field, tuples with the
 * same "user-id" will always go to the same task, but tuples with different
 * "user-id"'s may go to different tasks.
 *
 * @org.apache.xbean.XBean element="fieldSet" description="The stream is
 * partitioned by the fields specified in the grouping. For example, if the
 * stream is grouped by the "user-id" field, tuples with the same "user-id" will
 * always go to the same task, but tuples with different "user-id"'s may go to
 * different tasks."
 *
 * @author rmoquin
 */
public class FieldSetDistribution implements DistributionPolicy {
  private String[] fields;
  
  @Override
  public void setup(BoltDeclarer declarer, Subscription subscription) {
    declarer.fieldsGrouping(subscription.getTo(), subscription.getStream().getId(), new Fields(fields));
  }

  /**
   * @return the fields
   */
  public String[] getFields() {
    return fields;
  }

  /**
   * @param fields the fields to set
   */
  public void setFields(String[] fields) {
    this.fields = fields;
  }
}
