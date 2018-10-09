package com.tibco.ep.samples.javafunction;

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
        Configuration.forFile("engine.conf").load().activate();

        // create a StreamBase server and load modules once for all tests in this class
        server = ServerManagerFactory.getEmbeddedServer();
        server.startServer();
        server.loadApp("com.tibco.ep.samples.javafunction.JavaFunction");
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
     * Null things test
     * 
     * @throws StreamBaseException Test failed
     */
    @Test
    public void nullThings() throws StreamBaseException  {
    	LOGGER.info("Null things");
    	
        server.getEnqueuer("calculate").enqueue(
                JSONSingleQuotesTupleMaker.MAKER, "{}");
        new Expecter(server.getDequeuer("out")).expect(
                JSONSingleQuotesTupleMaker.MAKER, "{}");
    }

    /**
     * One thing test
     * 
     * @throws StreamBaseException Test failed
     */
    @Test
    public void oneThing() throws StreamBaseException {
    	LOGGER.info("One thing");
    	
        server.getEnqueuer("things")
        .enqueue(
                JSONSingleQuotesTupleMaker.MAKER,
                "{'name':'Cube','width':10.0,'height':10.0,'depth':10.0,'weightKg':1.0}");
        server.getEnqueuer("calculate").enqueue(
                JSONSingleQuotesTupleMaker.MAKER, "{}");
        new Expecter(server.getDequeuer("out")).expect(
                JSONSingleQuotesTupleMaker.MAKER, "{'density': '0.001'}");
    }

    /**
     * Two things test
     * 
     * @throws StreamBaseException Test failed
     */
    @Test
    public void twoThings() throws StreamBaseException {
    	LOGGER.info("Two things");
    	
        server.getEnqueuer("things")
        .enqueue(
                JSONSingleQuotesTupleMaker.MAKER,
                "{'name':'Cube','width':10.0,'height':10.0,'depth':10.0,'weightKg':1000.0}",
                "{'name':'Cube','width':1.0,'height':1.0,'depth':1.0,'weightKg':1.0}");
        server.getEnqueuer("calculate").enqueue(
                JSONSingleQuotesTupleMaker.MAKER, "{}");
        new Expecter(server.getDequeuer("out")).expect(
                JSONSingleQuotesTupleMaker.MAKER, "{'density': '1.0'}");
    }

    /**
     * Two unequal things test
     * 
     * @throws StreamBaseException Test failed
     */
    @Test
    public void twoUnequalThings() throws Exception {
    	LOGGER.info("Two unequal things");
    	
        server.getEnqueuer("things")
        .enqueue(
                JSONSingleQuotesTupleMaker.MAKER,
                "{'name':'Cube','width':10.0,'height':10.0,'depth':10.0,'weightKg':500.0}",
                "{'name':'Cube','width':1.0,'height':1.0,'depth':1.0,'weightKg':1.0}");
        server.getEnqueuer("calculate").enqueue(
                JSONSingleQuotesTupleMaker.MAKER, "{}");
        new Expecter(server.getDequeuer("out")).expect(
                JSONSingleQuotesTupleMaker.MAKER, "{'density': '0.75'}");
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
