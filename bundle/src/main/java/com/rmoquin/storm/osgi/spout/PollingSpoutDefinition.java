package com.rmoquin.storm.osgi.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Spout which will call it's configured
 * <code>ITupleSource</code> execute method at a predefined interval so it can put tuples onto the tuple queue. The
 * default poll interval is 5 seconds.
 *
 * @org.apache.xbean.XBean element="pollingSpout" description="A Spout which will call it's
 * configured <code>ITupleSource</code> execute method at a predefined interval so it can put tuples onto the tuple
 * queue. The default poll interval is 5 seconds.
 *
 * @see ITupleSource
 * @author rmoquin
 */
public class PollingSpoutDefinition extends SpoutDefinition {

  private static final Logger LOGGER = LoggerFactory.getLogger(PollingSpoutDefinition.class);
  private ScheduledExecutorService executorService;
  private int interval = 5;
  private TimeUnit unit = TimeUnit.SECONDS;

  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    super.open(conf, context, collector);
  }

  @Override
  public void activate() {
    super.activate();
    executorService = Executors.newScheduledThreadPool(1);
    executorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        try {
          PollingSpoutDefinition.this.getSource().execute();
        } catch (Exception ex) {
          LOGGER.warn("An error occurred while polling the tuple source.", ex);
        }
      }
    }, interval, interval, unit);
  }

  @Override
  public void deactivate() {
    this.executorService.shutdown();
    super.deactivate();
  }

  @Override
  public void close() {
    //In case the running executor hadn't shutdown yet after the initial call to shutdown.
    this.executorService.shutdownNow();
    super.close();
  }

  /**
   * @return the interval
   */
  public int getInterval() {
    return interval;
  }

  /**
   * @param interval the interval to set
   */
  public void setInterval(int interval) {
    this.interval = interval;
  }

  /**
   * @return the unit
   */
  public TimeUnit getUnit() {
    return unit;
  }

  /**
   * @param unit the unit to set
   */
  public void setUnit(TimeUnit unit) {
    this.unit = unit;
  }
}
