# Query table application archive

This application archive is part of the query table sample and shows how to deploy the query table fragment.

* [Query table eventflow fragment](../../../../querytable-eventflowfragment/src/site/markdown/index.md)

* [Application definition configuration](../../main/configurations/app.conf)

* [Node deployment configuration](../../main/configurations/defaultnode.conf)

* [Client API configuration](../../main/configurations/clientapi.conf)

* Integration tests in pom.xml :

```
            <plugin>
                <groupId>com.tibco.ep</groupId>
                <artifactId>ep-maven-plugin</artifactId>
                <extensions>true</extensions>
                <configuration>
                    <nodes>
                        <node>A</node>
                        <node>B</node>
                    </nodes>
                </configuration>

                <executions>

                    <execution>
                        <id>start nodes</id>
                        <phase>pre-integration-test</phase>
                        <goals><goal>start-nodes</goal></goals>
                    </execution>

                    <execution>
                        <id>Run playback</id>
                        <phase>integration-test</phase>
                        <goals><goal>administer-nodes</goal></goals>
                        <configuration>
                            <command>start</command>
                            <target>playback</target>
                            <serviceName>A.${project.artifactId}</serviceName>
                            <skip>${skipTests}</skip>
                            <arguments>
                                <engine>default-engine-for-com.tibco.ep.samples.querytable_eventflowfragment</engine>
                                <connecttime>60</connecttime>
                                <maxtuples>10000</maxtuples>
                                <maxtime>60</maxtime>
                                <prefilltuplebuffer>false</prefilltuplebuffer>
                                <tuplebuffersize>100</tuplebuffersize>
                                <asfastaspossible>false</asfastaspossible>
                                <simulationfile>${project.basedir}/src/test/configurations/test.sbfs</simulationfile>
                            </arguments>
                        </configuration>
                    </execution>

                    <execution>
                        <id>Wait for playback to complete</id>
                        <phase>integration-test</phase>
                        <goals><goal>administer-nodes</goal></goals>
                        <configuration>
                            <command>wait</command>
                            <target>playback</target>
                            <serviceName>A.${project.artifactId}</serviceName>
                            <skip>${skipTests}</skip>
                        </configuration>
                    </execution>

                    <execution>
                        <id>Display objects</id>
                        <phase>integration-test</phase>
                        <goals><goal>administer-nodes</goal></goals>
                        <configuration>
                            <command>display</command>
                            <target>statistics</target>
                            <arguments>
                                <statistics>object</statistics>
                            </arguments>
                            <skip>${skipTests}</skip>
                        </configuration>
                    </execution>

                    <execution>
                        <id>stop nodes</id>
                        <phase>post-integration-test</phase>
                        <goals><goal>stop-nodes</goal></goals>
                    </execution>

                </executions>

            </plugin>

            <!-- wait for application to finish starting -->
            <!-- FIX THIS - see https://jira.tibco.com/browse/SB-33983 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <executions>
                    <execution>
                        <id>wait for applications to start</id>
                        <phase>pre-integration-test</phase>
                        <goals><goal>run</goal></goals>
                        <configuration>
                            <target>
                                <!-- wait for max 10 minutes checking every 500ms -->
                                <waitfor maxwait="10" maxwaitunit="minute" checkevery="500"  timeoutproperty="atimeoutqt">
                                    <resourcecontains resource="${project.build.directory}/test-nodes/A.${project.artifactId}/logs/default-engine-for-com.tibco.ep.samples.querytable_eventflowfragment.log" substring=" sbd at "/>
                                </waitfor>
                                <waitfor maxwait="10" maxwaitunit="minute" checkevery="500"  timeoutproperty="btimeoutqt">
                                    <resourcecontains resource="${project.build.directory}/test-nodes/B.${project.artifactId}/logs/default-engine-for-com.tibco.ep.samples.querytable_eventflowfragment.log" substring=" sbd at "/>
                                </waitfor>
                                <fail message="QueryTable on node A failed to startup in time">
                                    <condition>
                                        <isset property="atimeoutqt"/>
                                    </condition>
                                </fail>
                                <fail message="QueryTable on node B failed to startup in time">
                                    <condition>
                                        <isset property="btimeoutqt"/>
                                    </condition>
                                </fail>
                            </target>
                            <skip>${skipTests}</skip>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

Expected results :

```
...
[INFO] --- ep-maven-plugin:1.4.0-SNAPSHOT:administer-nodes (Run playback) @ querytable_application ---
[INFO] [A.querytable_application] Running "start playback"
[INFO] Node                       Playback Identifier 
[INFO] [A.querytable_application] 0                   
[INFO] [A.querytable_application] Finished "start playback"
[INFO] 
[INFO] --- ep-maven-plugin:1.4.0-SNAPSHOT:administer-nodes (Wait for playback to complete) @ querytable_application ---
[INFO] [A.querytable_application] Running "wait playback"
[INFO] Node                       Playback Identifier Status  Start Time                   Tuples Processed Tuples/Second Rate 
[INFO] [A.querytable_application] 0                   Stopped Wed Aug 22 14:43:49 BST 2018 10590            10.0               
[INFO] [A.querytable_application] Finished "wait playback"
[INFO] 
[INFO] --- ep-maven-plugin:1.4.0-SNAPSHOT:administer-nodes (Display objects) @ querytable_application ---
[INFO] [querytable_application] Running "display statistics"
[INFO] Node                       Class                                                                                   Cardinality Creates Destroys 
...
[INFO] [A.querytable_application] default.Bids_and_Asks                                                                   6           10000   9994     
...
[INFO] [B.querytable_application] default.Bids_and_Asks                                                                   6           10000   9994     
...

```