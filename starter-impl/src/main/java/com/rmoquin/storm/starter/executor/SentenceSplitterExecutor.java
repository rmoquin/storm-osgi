package com.rmoquin.storm.starter.executor;

import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.bolt.BoltExecutorException;
import com.rmoquin.storm.osgi.bolt.IBoltExecutor;
import java.util.Queue;

/**
 * An IBoltExecutor implementation which splits a sentence into words to be emitted.
 *
 * @org.apache.xbean.XBean element="sentenceSplitter" description="An IBoltExecutor implementation which splits a
 * sentence into words to be emitted."
 *
 * @author rmoquin
 */
public class SentenceSplitterExecutor implements IBoltExecutor {

  private Queue<Values> queue = null;

  @Override
  public void init(Queue<Values> queue) {
    this.queue = queue;
  }

  @Override
  public void execute(Tuple tuple) throws BoltExecutorException {
    String sentence = tuple.getString(0);
    String[] words = sentence.split(" ");
    for (String word : words) {
      this.queue.add(new Values(word));
    }
  }
}