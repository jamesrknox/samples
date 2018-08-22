# Introduction

This eventflow fragment contains the following :

* EventFlow that stores and queries bids in a query table

![Application](https://raw.githubusercontent.com/plord12/samples/master/applications/querytable/querytable-eventflowfragment/src/site/resources/images/Querytable.png)

* [Junit test case](../../test/java/com/tibco/ep/samples/querytable/TestCase.java)

Test application definition HOCON file :

```
name = "MyApplication"
version = "1.0"
type = "com.tibco.ep.dtm.configuration.application"
 
configuration = {
    ApplicationDefinition = {
 
        dataDistributionPolicies = {
            dynamic = {
                type = DYNAMIC
                dynamicDataDistributionPolicy = {
                    numberOfPartitions = 3
                    primaryDataRedundancy = {
                        numberOfReplicas = 6
                        replicationType = SYNCHRONOUS
                    }
                    backupDataRedundancy = {
                        numberOfReplicas = 4
                        replicationType = ASYNCHRONOUS
                    }
                }
            }
        }
 
    }
}

```

Test node deployment HOCON file :

```
name = "MyApplication"
version = "1.0"
type = "com.tibco.ep.dtm.configuration.node"
 
configuration = {
    NodeDeploy = {
        nodes = {
            "${EP_NODE_NAME}" = {
            }
        }
 
        availabilityZones = {
            DC1 = {
                dataDistributionPolicy = "dynamic"
             }
        }
 
    }
}
```

Test case :

```java
    /**
     * test case
     * 
     * @throws StreamBaseException on error
     */
    @Test
    public void test1() throws StreamBaseException {
        LOGGER.info("Test Case 1");

        Administration admin = new Administration();
        
        // should insert one row
        //
        server.getEnqueuer("WriteInput").enqueue(JSONSingleQuotesTupleMaker.MAKER, "{'time_int':1,'symbol':'AAA','bid_price':100, 'bid_size':1, 'ask_price':1.1, 'ask_size':1, 'sequence':1 }");

        // check dequeue
        //
        server.getEnqueuer("ReadInput").enqueue(JSONSingleQuotesTupleMaker.MAKER, "{'symbol':'AAA'}");

        // check transactional memory
        //
        for (int i=0; i<10; i++) {
            if (admin.execute("read", "querytable").toDtmResults().get().getResultSet().getRows().size() > 0) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        assertEquals("AAA,100.0,1.0", admin.execute("read", "querytable").toDtmResults().get().getResultSet().getRow(0).getColumn(1));

        // update one row
        //
        server.getEnqueuer("WriteInput").enqueue(JSONSingleQuotesTupleMaker.MAKER,"{'time_int':1,'symbol':'AAA','bid_price':200, 'bid_size':2, 'ask_price':2.2, 'ask_size':2, 'sequence':2 }");

        // check dequeue
        //
        server.getEnqueuer("ReadInput").enqueue(JSONSingleQuotesTupleMaker.MAKER, "{'symbol':'AAA'}");

        // check transactional memory
        //
        assertEquals("AAA,200.0,2.0", admin.execute("read", "querytable").toDtmResults().get().getResultSet().getRow(0).getColumn(1));

        // non-existing
        //
        server.getEnqueuer("ReadInput").enqueue(JSONSingleQuotesTupleMaker.MAKER, "{'symbol':'BBB'}");
    }
```

Results :

```
[INFO] --- ep-maven-plugin:1.4.0-SNAPSHOT:test-eventflow-fragment (default-test-eventflow-fragment) @ querytable_eventflowfragment ---
[INFO] [dtm] INFO: Deploy tool version: [TIBCO Distributed Transactional Memory Platform 10.4.0-SNAPSHOT (build 1808220850)] starting at [Wed Aug 22 11:50:26 BST 2018]
[INFO] [dtm] INFO: Node version: [TIBCO StreamBase Runtime 10.4.0-SNAPSHOT (build 1808220859)]
[INFO] [dtm] INFO: Starting com.tibco.ep.buildmavenplugin.surefire.Runner on node A.querytable_eventflowfragment ...
[INFO] [dtm] INFO: com.tibco.ep.buildmavenplugin.surefire.Runner started on JVM com_tibco_ep_buildmavenplugin_surefire_Runner0 on node A.querytable_eventflowfragment.
[INFO] [A.querytable_eventflowfragment] No user-defined Logback configuration, using product default configuration
[INFO] [A.querytable_eventflowfragment] 2018-08-22 11:50:30.572000+0100 [81977:main] INFO  com.tibco.ep.dtm.stdout:
[INFO] [A.querytable_eventflowfragment] -------------------------------------------------------
[INFO] [A.querytable_eventflowfragment]  T E S T S
[INFO] [A.querytable_eventflowfragment] -------------------------------------------------------
[INFO] [A.querytable_eventflowfragment] Running com.tibco.ep.samples.querytable.TestCase
[INFO] [A.querytable_eventflowfragment] Test Case 1
[INFO] [A.querytable_eventflowfragment] WriteLog(tupleid=0,symbol="AAA",bid_price=100.0,bid_size=1.0)
[INFO] [A.querytable_eventflowfragment] ReadLog(tupleid=0,symbol="AAA",bid_price=100.0,bid_size=1.0)
[INFO] [A.querytable_eventflowfragment] WriteLog(tupleid=0,symbol="AAA",bid_price=200.0,bid_size=2.0)
[INFO] [A.querytable_eventflowfragment] ReadLog(tupleid=0,symbol="AAA",bid_price=200.0,bid_size=2.0)
[INFO] [A.querytable_eventflowfragment] ReadLog(tupleid=0,symbol="null",bid_price=null,bid_size=null)
[INFO] [A.querytable_eventflowfragment] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.549 sec
[INFO] [A.querytable_eventflowfragment] 2018-08-22 11:50:35.127000+0100 [81977:main] INFO  com.tibco.ep.dtm.stdout:
[INFO] [A.querytable_eventflowfragment] Results :
[INFO] [A.querytable_eventflowfragment] 2018-08-22 11:50:35.127000+0100 [81977:main] INFO  com.tibco.ep.dtm.stdout:
[INFO] [A.querytable_eventflowfragment] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] [A.querytable_eventflowfragment] 2018-08-22 11:50:35.127000+0100 [81977:main] INFO  com.tibco.ep.dtm.stdout:
[INFO] [dtm] INFO: Engine com_tibco_ep_buildmavenplugin_surefire_Runner0 on node [A.querytable_eventflowfragment] exited with status [0]
[INFO] [querytable_eventflowfragment] Finished "junit"
```