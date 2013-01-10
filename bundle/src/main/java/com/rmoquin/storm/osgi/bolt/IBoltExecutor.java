package com.rmoquin.storm.osgi.bolt;

import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import java.io.Serializable;
import java.util.Queue;

/**
 *
 * @author rmoquin
 */
public interface IBoltExecutor extends Serializable {

  /**
   * Initializes this source with a tuple queue to place tuples for output by the containing bolt.
   *
   * @param queue
   */
  public void init(Queue<Values> queue);

  /**
   * Executes logic for a bolt, and if there is anything the bolt wants to have output automatically, it should place it
   * on the injected tuple queue.
   */
  public void execute(Tuple tuple) throws BoltExecutorException;
}
