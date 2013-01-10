Storm OSGi
==========

Porting the Storm Project to run on OSGi and take avantage of the features it provides.
 
Preparation
==========

1. Clone this project (either head or from tag).
2. Build with Maven _mvn clean install_
3. Download a copy of [Apache Karaf 3.0.0-SNAPSHOT][https://repository.apache.org/content/groups/snapshots-group/org/apache/karaf/apache-karaf/3.0.0-SNAPSHOT/apache-karaf-3.0.0-20121231.002157-832.zip] (It should be released soon) from here, any future version of the snapshot should be fine: 

4. Extract the jar file into a location of your choice, you should end up with a directory named:

* _apache-karaf-3.0.0-SNAPSHOT_

5. Start Karaf

* Windows: _apache-karaf-3.0.0-SNAPSHOT\bin\karaf.bat_
* Linux: _apache-karaf-3.0.0-SNAPSHOT/bin/karaf_
* 
6. The Karaf console will load:

        __ __                  ____
       / //_/____ __________ _/ __/
      / ,<  / __ `/ ___/ __ `/ /_
     / /| |/ /_/ / /  / /_/ / __/
    /_/ |_|\__,_/_/   \__,_/_/

  Apache Karaf (3.0.0-SNAPSHOT)

Hit '<tab>' for a list of available commands
and '[cmd] --help' for help on a specific command.
Hit '<ctrl-d>' or type 'system:shutdown' or 'logout' to shutdown Karaf.

karaf@root()>

7. Deploy the starter-features file:

* Copy _storm-osgi/starter-features/target/classes_ to _apache-karaf-3.0.0-SNAPSHOT_/deploy 

-----------------------------------------------

Quick Start
==========

1.  Install the Storm Starter bundle by entering the following on the karaf console:

* (`features:install storm-starter`)

2. Install any of the 3 provided starter topologies, they will be deployed to the same local cluster and started automatically.

* (`features:install exclamation-topology`)
* (`features:install word-count-java-topology`)
* (`features:install word-count-python-topology`)

3. Tail the logs for the topologies:

* (`log:tail`)

4. Hit (`Ctrl-C`) to stop tailing the log.

5. Shut down Karaf:

* (`system:shutdown`)

In Depth Start
==========

1.  View the list of what's installed:

karaf@root()> *bundle:list*
START LEVEL 100 , List Threshold: 50
   ID   State         Level Name
[  49] [    Active] [   80] storm-starter-features (0.0.0)

2. You can view the available features on this Karaf instance by entering, feature:info, and you'll see something similar to the following:

karaf@root()> feature:info
application-without-isolation   aries-annotation                config                          eventadmin                      exclamation-topology
gemini-blueprint                http                            http-whiteboard                 jasypt-encryption               jetty
jndi                            jpa                             kar                             management                      obr
package                         region                          scheduler                       scr                             spring
spring-aspects                  spring-dm                       spring-dm-web                   spring-instrument               spring-jdbc
spring-jms                      spring-orm                      spring-oxm                      spring-struts                   spring-test
spring-tx                       spring-web                      spring-web-portlet              ssh                             standard
storm-starter                   transaction                     war                             webconsole                      word-count-java-topology
word-count-python-topology      wrapper

3.  Install the Storm Starter bundle by entering the following on the karaf console:

* (`features:install storm-starter`)

4.  Listing the bundles again, will show you the bundles installed for storm starter which will look similar to:

karaf@root()> *bundle:list*
START LEVEL 100 , List Threshold: 50
   ID   State         Level Name
[  49] [    Active] [   80] storm-starter-features.xml (0.0.0)
[  76] [    Active] [   80] Apache XBean :: OSGI Blueprint Namespace Handler (3.13.0.SNAPSHOT)
[  77] [    Active] [   80] Commons JEXL (2.0)
[  78] [    Active] [   80] kryo (2.17.0)
[  79] [    Active] [   80] Clojure OSGi (1.4.0)
[  80] [    Active] [   80] clojure (1.4.0)
[  81] [    Active] [   80] carbonite (1.5.0)
[  82] [    Active] [   80] storm (0.9.0.SNAPSHOT)
[  83] [    Active] [   80] Storm OSGI (0.9.0.SNAPSHOT), Fragments: 108
[  84] [    Active] [   80] SnakeYAML (1.9.0)
[  85] [    Active] [   80] Jetty Utilities (6.1.26)
[  86] [    Active] [   80] Servlet Specification API (2.5)
[  87] [    Active] [   80] Jetty Server (6.1.26)
[  88] [    Active] [   80] json-simple (1.1.0)
[  89] [    Active] [   80] Joda-Time (2.1)
[  90] [    Active] [   80] jgrapht (0.8.3)
[  91] [    Active] [   80] jeromq (0.2.0)
[  92] [    Active] [   80] ZooKeeper Bundle (3.3.3)
[  93] [    Active] [   80] Apache HttpCore OSGi bundle (4.1.1)
[  94] [    Active] [   80] Apache HttpClient OSGi bundle (4.1.1)
[  95] [    Active] [   80] Commons Lang (2.5)
[  96] [    Active] [   80] libthrift7 (0.7.0)
[  97] [    Active] [   80] Apache Commons IO Bundle (1.4)
[  98] [    Active] [   80] Apache Commons FileUpload Bundle (1.2.1)
[  99] [    Active] [   80] Commons Exec (1.1)
[ 100] [    Active] [   80] Commons Codec (1.4)
[ 101] [    Active] [   80] curator-client (1.0.1)
[ 102] [    Active] [   80] Guava: Google Core Libraries for Java (13.0.0)
[ 103] [    Active] [   80] curator-framework (1.0.1)
[ 104] [    Active] [   80] disruptor (2.10.1)
[ 105] [    Active] [   80] Objenesis (1.2)
[ 106] [    Active] [   80] reflectasm-shaded (1.07.0)
[ 107] [    Active] [   80] minlog (1.2.0)
[ 108] [  Resolved] [   80] Storm Starter (0.9.0.SNAPSHOT), Hosts: 83

5. Install any of the 3 provided starter topologies, they will be deployed to the same local cluster and started automatically.

* (`features:install exclamation-topology`)
* (`features:install word-count-java-topology`)
* (`features:install word-count-python-topology`)

6. Tail the logs for the topologies:

* (`log:tail`)

7. Hit (`Ctrl-C`) to stop tailing the log.

8. Updating or Stopping topology bundles

   * You can use (`bundle:list`) to get the id of a bundle you want to work with
   * To stop a topology bundle, enter the following but substitute the topology bundle id, ex:

   ** karaf@root()> *bundle:stop 88*

   * You can modify a topology blueprint xml file, build the maven project, then apply the update on the fly using the bundle id, ex:

   ** karaf@root()> *bundle:update 88*

   * Updating or stopping a bundle will cause the topology to be automatically undeployed from the cluster and redeployed with the changes.

8. Shut down Karaf.  Storm OSGi will automatically stop all topologies and undeploy them before the system shuts down:

* (`system:shutdown`)
