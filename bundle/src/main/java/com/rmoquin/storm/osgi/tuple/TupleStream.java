package com.rmoquin.storm.osgi.tuple;

/**
 * @org.apache.xbean.XBean element="stream"
 * @author rmoquin
 */
public class TupleStream implements ITupleStream {
  private String id;
  private String[] schema;

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the schema
   */
  @Override
  public String[] getSchema() {
    return schema;
  }

  /**
   * @param schema the schema to set
   */
  @Override
  public void setSchema(String[] schema) {
    this.schema = schema;
  }
}
