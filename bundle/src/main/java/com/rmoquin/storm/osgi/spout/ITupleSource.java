package com.rmoquin.storm.osgi.spout;

import backtype.storm.tuple.Values;
import java.io.Serializable;
import java.util.Queue;

/**
 *
 * @author rmoquin
 */
public interface ITupleSource extends Serializable {

  /**
   * Initializes this source with a tuple queue to place tuples for output by the containing spout.
   *
   * @param queue
   */
  public void init(Queue<Values> queue);
  
  /**
   * The tuple source should execute some logic to, at a minimum, place a tuple on the tuple queue set by the containing
   * component. This method can be polled by the containing component, or could be invoked by a third party listener (a
   * JMS client for example) with data to process and put onto the tuple queue.
   */
  public void execute();
}
