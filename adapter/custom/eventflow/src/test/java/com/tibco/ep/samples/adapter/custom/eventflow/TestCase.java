package com.tibco.ep.samples.adapter.custom.eventflow;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.streambase.sb.StreamBaseException;
import com.streambase.sb.unittest.Expecter;
import com.streambase.sb.unittest.JSONSingleQuotesTupleMaker;
import com.streambase.sb.unittest.SBServerManager;
import com.streambase.sb.unittest.ServerManagerFactory;

import com.tibco.ep.testing.framework.Configuration;
import com.tibco.ep.testing.framework.ConfigurationException;
import com.tibco.ep.testing.framework.TransactionalDeadlockDetectedException;
import com.tibco.ep.testing.framework.TransactionalMemoryLeakException;
import com.tibco.ep.testing.framework.UnitTest;

/**
 * Example test case
 */
public class TestCase extends UnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCase.class);

    private static SBServerManager server;

    /**
     * Set up the server
     *
     * @throws StreamBaseException on start server error
     * @throws ConfigurationException on configuration failure
     * @throws InterruptedException on start server error
     */
    @BeforeClass
    public static void setupServer() throws StreamBaseException, ConfigurationException, InterruptedException {
        // Example configuration load
        // Configuration.forFile("engine.conf").load().activate();

        // create a StreamBase server and load modules once for all tests in this class
        server = ServerManagerFactory.getEmbeddedServer();
        server.startServer();
        server.loadApp("com.tibco.ep.samples.adapter.custom.eventflow.EventFlow");
    }

    /**
     * Stop the server
     *
     * @throws StreamBaseException on shutdown failure
     * @throws InterruptedException on shutdown failure
     */
    @AfterClass
    public static void stopServer() throws InterruptedException, StreamBaseException {
        try {
            assertNotNull(server);
            server.shutdownServer();
            server = null;
        } finally {
            Configuration.deactiveAndRemoveAll();
        }
    }

    /**
     * Start the containers
     *
     * @throws StreamBaseException on start container error
     */
    @Before
    public void startContainers() throws StreamBaseException {
        // before each test, startup fresh container instances
        server.startContainers();

        // Setup test framework before running tests
        this.initialize();
    }

    /**
     * Test 1
     * 
     * @throws StreamBaseException Test failed
     */
    @Test
    public void test1() throws StreamBaseException {
    	LOGGER.info("Test 1");
    	
        server.getEnqueuer("TradeIn").enqueue(JSONSingleQuotesTupleMaker.MAKER,
                "{'symbol':'acme','price':22.0}",
                "{'symbol':'ibm','price':200.0}",
                "{'symbol':'aapl','price':2000.0}");
        new Expecter(server.getDequeuer("SmallTrade")).expect(
                JSONSingleQuotesTupleMaker.MAKER,
                "{'symbol':'acme','price':22.0,'quantity':1000}");
        new Expecter(server.getDequeuer("BigTrade")).expect(
                JSONSingleQuotesTupleMaker.MAKER,
                "{'symbol':'ibm','price':200.0,'quantity':1000}",
                "{'symbol':'aapl','price':2000.0,'quantity':1000}");
    }

    /**
     * Stop containers
     *
     * @throws StreamBaseException on stop container error
     * @throws TransactionalMemoryLeakException Leak detected
     * @throws TransactionalDeadlockDetectedException Deadlock detected
     */
    @After
    public void stopContainers() throws StreamBaseException, TransactionalMemoryLeakException, TransactionalDeadlockDetectedException {
        // Complete test framework and check for any errors
        this.complete();

        // after each test, dispose of the container instances
        server.stopContainers();
    }
}
