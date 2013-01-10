package com.rmoquin.storm.starter.source;

import backtype.storm.tuple.Values;
import com.rmoquin.storm.osgi.spout.ITupleSource;
import java.util.Queue;
import java.util.Random;

/**
 * An IPollingTupleSource which puts a random sentence on it's Spout's tuple
 * queue each poll interval.
 *
 * @org.apache.xbean.XBean element="randomSentence" description="A ITupleSource
 * which randomly puts one of the configured sentences onto it's Spout's tuple
 * queue on each call of execute."
 *
 * @author rmoquin
 */
public class RandomSentence implements ITupleSource {

  private Random rand;
  private String[] sentences;
  private Queue<Values> queue = null;

  @Override
  public void init(Queue<Values> queue) {
    rand = new Random();
    this.queue = queue;
  }

  @Override
  public void execute() {
    String sentence = sentences[rand.nextInt(sentences.length)];
    this.queue.add(new Values(sentence));
  }

  /**
   * @return the sentences
   */
  public String[] getSentences() {
    return sentences;
  }

  /**
   * @param sentences the sentences to set
   */
  public void setSentences(String[] sentences) {
    this.sentences = sentences;
  }
}
