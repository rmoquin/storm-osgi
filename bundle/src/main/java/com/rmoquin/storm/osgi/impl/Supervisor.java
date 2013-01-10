package com.rmoquin.storm.osgi.impl;

import backtype.storm.scheduler.ISupervisor;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rmoquin
 */
public class Supervisor implements ISupervisor {

  private static final Logger LOGGER = LoggerFactory.getLogger(Supervisor.class);

  @Override
  public void prepare(Map stormConf, String schedulerLocalDir) {
    LOGGER.error("In supervisor method.", new Exception());
  }

  @Override
  public String getSupervisorId() {
    LOGGER.error("In supervisor method.", new Exception());
    return null;
  }

  @Override
  public String getAssignmentId() {
    LOGGER.error("In supervisor method.", new Exception());
    return null;
  }

  @Override
  public Object getMetadata() {
    LOGGER.error("In supervisor method.", new Exception());
    return null;
  }

  @Override
  public boolean confirmAssigned(int port) {
    LOGGER.error("In supervisor method.", new Exception());
    return true;
  }

  @Override
  public void killedWorker(int port) {
    LOGGER.error("In supervisor method.", new Exception());
  }

  @Override
  public void assigned(Collection<Integer> ports) {
    LOGGER.error("In supervisor method.", new Exception());
  }
}
