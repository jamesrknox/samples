# HA : 2-node active standby

This sample describes how to deploy an EventFlow fragment in a 2-node active standby configuration.

* [Machines and nodes](#machines-and-nodes)
* [Data partitioning](#data-partitioning)
* [Define the application definition configuration](#define-the-application-definition-configuration)
* [Define the node deployment configuration](#define-the-node-deployment-configuration)
* [Design notes](#design-notes)
* [Failure scenarios](#failure-scenarios)
* [Building this sample from the command line and running the integration test cases](#building-this-sample-from-the-command-line-and-running-the-integration-test-cases)

## Machines and nodes

In this sample we name the initially active machine as **A** which hosts the StreamBase node **A**.  
The standby machine is named **B** which hosts the StreamBase node **B**.

![nodes](images/two-node-active-standby-nodes.svg)

A client that uses the service initially connects to machine **A**.

( service names are omitted in descriptions for clarity )

## Data partitioning

To support an active standby configuration, the query table data must be replicated from the active node to the
standby.  In this sample a static partition **P1** is defined that is initially active on node **A**
with a replica defined on node **B** :

![partitions](images/two-node-active-standby-partitions.svg)

## Define the application definition configuration

An application definition configuration defines the data distribution policy referenced in the EventFlow fragment 
data distribution policy :


```scala
name = "as-2node-app"
version = "1.0.0"
type = "com.tibco.ep.dtm.configuration.application"

configuration = {
    ApplicationDefinition = {
        execution {
            nodeTypes {
                docker {
                    sharedMemory = {
                        memoryType = SYSTEM_V_SHARED_MEMORY
                    }
                }
            }
        }
        dataDistributionPolicies = {
            static-data-distribution-policy = {
                type = STATIC
            }
        }
    }
}
```

## Define the node deployment configuration

A node deployment configuration defines the availability zone :

```scala
name = "as-2node-app"
version = "1.0.0"
type = "com.tibco.ep.dtm.configuration.node"

configuration = {
    NodeDeploy = {
        nodes = {
            "A.as-2node-app" = {
                engines = {
                    as-2node-ef = {
                        fragmentIdentifier = "com.tibco.ep.samples.highavailability.as-2node-ef"                                                                
                    }                                                    
                }
                availabilityZoneMemberships = {
                    static-availability-zone = {
                        staticPartitionBindings = {
                            P1 = {
                                type = ACTIVE
                            }
                        }
                    }
                }
            }
            "B.as-2node-app" = {
                engines = {
                    as-2node-ef = {
                        fragmentIdentifier = "com.tibco.ep.samples.highavailability.as-2node-ef"                                                                
                    }                                                    
                }
                availabilityZoneMemberships = {
                    static-availability-zone = {
                        staticPartitionBindings = {
                            P1 = {
                                type = REPLICA
                            }
                        }
                    }
                }
            }
        }
        availabilityZones = {
            static-availability-zone = {
                dataDistributionPolicy = static-data-distribution-policy
                staticPartitionPolicy = {
                    staticPartitions = {
                        P1 = {
                        }
                    }
                }
            }
        }
    }
}
```

## Design notes

* A static data distribution policy is chosen ( over the default dynamic data distribution policy ) to specifically set the partition as initially active on A
* Most of the data distribution policy and the availability zone configuration values are not set since defaults work well


## Failure scenarios

The main failure cases for this deployment are outlined below :

Failure case   | Behaviour on failure | Steps to resolve | Notes
--- | --- | --- | ---
Machine A fails | 1 Client is disconnected<br/>2 Partition becomes active on B<br/>3 Client may connect to B and continue  | 1 Fix machine A<br/>2 Use **epadmin install node** and **epadmin start node** | 1 No data loss<br/>2 No service loss
Machine B fails | 1 Data replica is lost<br/>2 Client may continue work on A | 1 Fix machine B<br/>2 Use **epadmin install node** and **epadmin start node** | 1 No data loss<br/>2 No service loss
Network fails  | 1 Partition becomes active on both A and B<br/>**multi-master** scenario | 1 Fix network<br/>2 Use **epadmin restore availabilityzone** | 1 **Possible data loss**<br/>2 No service loss

With a 2 node configuration node quorums don't apply hence a multi-master scenario is possible on network failure.  
To avoid the risk of data loss when restoring the availability zone, multiple network paths ( such as network bonding )
is recommended.

## Building this sample from the command line and running the integration test cases

In this sample, some HA integration test cases are defined in the pom.xml that :

* start nodes A & B
* use **epadmin start playback** to inject tuples to node A
* use **epadmin read querytable** on node A to verify query table contents
* stop node A
* use **epadmin read querytable** on node B to verify no data loss
* stop node B

:warning: This does not constitute an exhaustive non-functional test plan

Use the [maven](https://maven.apache.org) as **mvn install** to build from the command line or Continuous Integration system :

![maven](images/maven.gif)
