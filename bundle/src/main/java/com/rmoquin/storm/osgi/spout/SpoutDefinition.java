package com.rmoquin.storm.osgi.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.topology.ComponentDefinition;
import com.rmoquin.storm.osgi.tuple.TupleStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @org.apache.xbean.XBean element="spout"
 *
 * @author rmoquin
 */
public class SpoutDefinition extends ComponentDefinition implements IRichSpout {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpoutDefinition.class);
  private ITupleSource source;
  private SpoutOutputCollector collector;
  private Queue<Values> tupleQueue;

  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    if (this.source == null) {
      throw new IllegalStateException("A tuple source implementation must be configured.");
    }
    this.collector = collector;
    tupleQueue = new ConcurrentLinkedQueue<Values>();
    this.source.init(this.tupleQueue);
  }

  @Override
  public void close() {
    this.tupleQueue.clear();
    this.tupleQueue = null;
  }

  @Override
  public void activate() {
  }

  @Override
  public void deactivate() {
  }

  @Override
  public void nextTuple() {
    Values output;
    while ((output = this.tupleQueue.poll()) != null) {
      for (TupleStream stream : super.getStreams()) {
        collector.emit(stream.getId(), output);
      }
    }
  }

  @Override
  public void ack(Object msgId) {
  }

  @Override
  public void fail(Object msgId) {
  }

  /**
   * @return the source
   */
  public ITupleSource getSource() {
    return this.source;
  }

  /**
   * @param source the source to set
   */
  public void setSource(ITupleSource source) {
    this.source = source;
  }
}
