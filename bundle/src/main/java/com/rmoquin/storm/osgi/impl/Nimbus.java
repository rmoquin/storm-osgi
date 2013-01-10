package com.rmoquin.storm.osgi.impl;

import backtype.storm.scheduler.INimbus;
import backtype.storm.scheduler.IScheduler;
import backtype.storm.scheduler.SupervisorDetails;
import backtype.storm.scheduler.Topologies;
import backtype.storm.scheduler.WorkerSlot;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rmoquin
 */
public class Nimbus implements INimbus {

  private static final Logger LOGGER = LoggerFactory.getLogger(Nimbus.class);

  @Override
  public void prepare(Map stormConf, String schedulerLocalDir) {
    LOGGER.error("In prepare.", new Exception());
  }

  @Override
  public Collection<WorkerSlot> allSlotsAvailableForScheduling(Collection<SupervisorDetails> existingSupervisors, Topologies topologies, Set<String> topologiesMissingAssignments) {
    LOGGER.error("In all available slots.", new Exception());
    return null;
  }

  @Override
  public void assignSlots(Topologies topologies, Map<String, Collection<WorkerSlot>> newSlotsByTopologyId) {
    LOGGER.error("In assignSlots.", new Exception());
  }

  @Override
  public String getHostName(Map<String, SupervisorDetails> existingSupervisors, String nodeId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public IScheduler getForcedScheduler() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
