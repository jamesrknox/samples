/*******************************************************************************
 *
 * COPYRIGHT
 *      Copyright 2018 TIBCO Software Inc. ALL RIGHTS RESERVED.
 *      TIBCO Software Inc. Confidential Information
 *      
 *******************************************************************************/
package com.tibco.ep.samples.adapter.custom;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.streambase.sb.unittest.Expecter;
import com.streambase.sb.unittest.JSONSingleQuotesTupleMaker;
import com.streambase.sb.unittest.SBServerManager;
import com.streambase.sb.unittest.ServerManagerFactory;
import com.tibco.ep.testing.framework.Configuration;
import com.tibco.ep.testing.framework.UnitTest;

/**
 * Adapter test
 */
public class AdapterTest extends UnitTest {

    private static SBServerManager server;

    /**
     * Set up embedded server
     * @throws Exception Server start failed
     */
    @BeforeClass
    public static void setupServer() throws Exception {
        // Example configuration load
        // Configuration.forFile("engine.conf").load().activate();
        
        // create a StreamBase server and load modules once for all tests in this class
        server = ServerManagerFactory.getEmbeddedServer();
        server.startServer();
        server.loadApp("com.tibco.ep.samples.adapter.custom.Test");
    }

    /**
     * Stop embedded server
     * @throws Exception Server stop failed
     */
    @AfterClass
    public static void stopServer() throws Exception {
        try {
        	assertNotNull(server);
        	server.shutdownServer();
        	server = null;
        } finally {
            Configuration.deactiveAndRemoveAll();
        }
    }

    /**
     * Start containers
     * @throws Exception Container start failed
     */
    @Before
    public void startContainers() throws Exception {
        // before each test, startup fresh container instances
        server.startContainers();

        // Setup test framework before running tests
        this.initialize();
    }

    /**
     * test 1
     * @throws Exception Test failed
     */
    @Test
    public void test1() throws Exception {
        server.getEnqueuer("InputStream").enqueue(JSONSingleQuotesTupleMaker.MAKER, "{'name':'fred'}");
        new Expecter(server.getDequeuer("OutputStream")).expect(JSONSingleQuotesTupleMaker.MAKER, "{'name':'fred'}");
    }

    /**
     * Stop containers
     * @throws Exception Container stop failed
     */
    @After
    public void stopContainers() throws Exception {
        // Complete test framework and check for any errors
        this.complete();

        // after each test, dispose of the container instances
        server.stopContainers();
    }

}
