package com.rmoquin.storm.osgi.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.topology.ComponentDefinition;
import com.rmoquin.storm.osgi.topology.Subscription;
import com.rmoquin.storm.osgi.tuple.TupleStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The parent definition for bolts, it should be subclassed to make it easier to assign a tag configuration for each
 * implementation.
 *
 * @org.apache.xbean.XBean element="bolt"
 * 
 * @author rmoquin
 */
public class BoltDefinition extends ComponentDefinition implements IRichBolt {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoltDefinition.class);
  private Subscription subscription;
  private IBoltExecutor executor;
  private OutputCollector collector;
  protected TopologyContext context;
  private Queue<Values> tupleQueue;

  @PostConstruct
  public void initBolt() {
    if (this.executor == null) {
      throw new IllegalStateException("This BoltDefinition requires a BoltExecutor to be specified.");
    }
  }

  @Override
  public void prepare(Map configuration, TopologyContext context, OutputCollector collector) {
    this.collector = collector;
    this.context = context;
    if (this.configuration != null) {
      this.configuration.putAll(configuration);
    } else {
      this.configuration = configuration;
    }
    tupleQueue = new ConcurrentLinkedQueue<Values>();
    this.executor.init(this.tupleQueue);
  }

  @Override
  public void execute(Tuple tuple) {
    try {
      this.executor.execute(tuple);
    } catch (BoltExecutorException ex) {
      LOGGER.warn("Error acknowledging the processing of the received tuple.", ex);
      //I'd assume we wouldn't want to go any further at this point, some error handling should be configurable.
      return;
    }
    try {
      this.collector.ack(tuple);
    } catch (Exception ex) {
      LOGGER.warn("Error acknowledging the processing of the received tuple, processing will continue.", ex);
    }
    if (this.getStreams() == null) {
      LOGGER.warn("An output stream/schema was defined for this bolt but it's executor returned null, no tuple will be emitted!");
      return;
    }
    Values output;
    while ((output = this.tupleQueue.poll()) != null) {
      for (TupleStream stream : super.getStreams()) {
        collector.emit(stream.getId(), output);
      }
    }
  }

  @Override
  public void cleanup() {
    //Why bother, this isn't guarenteed to be called anyhow....
    this.tupleQueue.clear();
    this.tupleQueue = null;
  }

  /**
   * @return the executor
   */
  public IBoltExecutor getExecutor() {
    return executor;
  }

  /**
   * @param executor the executor to set
   */
  public void setExecutor(IBoltExecutor executor) {
    this.executor = executor;
  }

  /**
   * The details of what this bolt is subscribed to.
   *
   * @return the subscription
   */
  public Subscription getSubscription() {
    return subscription;
  }

  /**
   * Sets the details of what this bolt is subscribed to.
   *
   * @param subscription the subscription to set
   */
  public void setSubscription(Subscription subscription) {
    this.subscription = subscription;
  }
}
