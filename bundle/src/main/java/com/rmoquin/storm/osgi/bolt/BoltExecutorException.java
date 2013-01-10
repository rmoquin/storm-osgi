package com.rmoquin.storm.osgi.bolt;

/**
 *
 * @author rmoquin
 */
public class BoltExecutorException extends Exception {

  public BoltExecutorException() {
  }

  public BoltExecutorException(String message) {
    super(message);
  }

  public BoltExecutorException(String message, Throwable cause) {
    super(message, cause);
  }

  public BoltExecutorException(Throwable cause) {
    super(cause);
  }
}
