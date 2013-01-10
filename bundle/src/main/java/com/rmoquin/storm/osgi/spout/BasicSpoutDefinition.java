package com.rmoquin.storm.osgi.spout;

/**
 * Implements a simple configurable BasicSpout which processes tuples placed on
 * it's tuple queue by a tuple source.
 *
 * @org.apache.xbean.XBean element="basicSpout" description="Implements a basic
 * configurable spout which emits tuples enqueued by the configured tuple source
 * implementation.  The tuple source is expected to have it's own method of triggering
 * it's execute method.
 *
 * @see ITupleSource
 * @author rmoquin
 */
public class BasicSpoutDefinition extends SpoutDefinition {
}
