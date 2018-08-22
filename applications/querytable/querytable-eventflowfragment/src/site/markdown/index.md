# Introduction

This eventflow fragment contains the following :

* EventFlow that stores and queries bids in a query table

![Application](https://raw.githubusercontent.com/plord12/samples/master/applications/querytable/querytable-eventflowfragment/src/site/resources/images/Querytable.png)

* [Junit test case](../../test/java/com/tibco/ep/samples/querytable/TestCase.java)

Test application definition HOCON file :

```hocon
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

```hocon
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