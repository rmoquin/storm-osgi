package com.rmoquin.storm.osgi.tuple;

import java.io.Serializable;

/**
 *
 * @author rmoquin
 */
public interface ITupleStream extends Serializable {

  public String getId();

  public void setId(String id);

  public String[] getSchema();

  public void setSchema(String[] schema);
}
