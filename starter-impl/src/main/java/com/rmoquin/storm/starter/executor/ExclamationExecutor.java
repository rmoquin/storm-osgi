package com.rmoquin.storm.starter.executor;

import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.bolt.BoltExecutorException;
import com.rmoquin.storm.osgi.bolt.IBoltExecutor;
import java.util.Queue;

/**
 * An IBoltExecutor implementation which appends exclamations onto the words it receives.
 *
 * @org.apache.xbean.XBean element="exclamation" description="A demo executor for the exclamation topology demo."
 *
 * @author rmoquin
 */
public class ExclamationExecutor implements IBoltExecutor {
  private Queue<Values> queue = null;

  @Override
  public void init(Queue<Values> queue) {
    this.queue = queue;
  }
  
  @Override
  public void execute(Tuple tuple) throws BoltExecutorException {
    this.queue.add(new Values(tuple.getString(0) + "!!!"));
  }
}
