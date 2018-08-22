package com.tibco.ep.samples.querytable;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.streambase.sb.StreamBaseException;
import com.streambase.sb.unittest.JSONSingleQuotesTupleMaker;
import com.streambase.sb.unittest.SBServerManager;
import com.streambase.sb.unittest.ServerManagerFactory;
import com.tibco.ep.testing.framework.Administration;
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
        Configuration.forFile("application.conf").load().activate();
        Configuration.forFile("node.conf").load().activate();
        
        // create a StreamBase server and load applications once for all tests in this class
        server = ServerManagerFactory.getEmbeddedServer();
        server.startServer();
        server.loadApp("com.tibco.ep.samples.querytable.querytable_eventflowfragment");
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
            if (server != null) {
                server.shutdownServer();
                server = null;
            }
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
