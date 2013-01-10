package com.rmoquin.storm.starter.executor;

import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.bolt.BoltExecutorException;
import com.rmoquin.storm.osgi.bolt.IBoltExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * An IBoltExecutor implementation which accepts tuples containing a single word, it then proceeds to count the number of duplicate words.
 *
 * @org.apache.xbean.XBean element="wordCounter" description="Provides word counting functionality for a Storm Bolt."
 *
 * @author rmoquin
 */
public class WordCountExecutor implements IBoltExecutor {

  private Map<String, Integer> counts = new HashMap<String, Integer>();
  private Queue<Values> queue = null;

  @Override
  public void init(Queue<Values> queue) {
    this.queue = queue;
  }
  
  @Override
  public void execute(Tuple tuple) throws BoltExecutorException {
    String word = tuple.getString(0);
    Integer count = counts.get(word);
    if (count == null) {
      count = 0;
    }
    count++;
    counts.put(word, count);
    this.queue.add(new Values(word, count));
  }
}
