/*******************************************************************************
 *
 * COPYRIGHT
 *      Copyright 2018 TIBCO Software Inc. ALL RIGHTS RESERVED.
 *      TIBCO Software Inc. Confidential Information
 *      
 *******************************************************************************/
package com.tibco.ep.samples.javafunction;


import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.streambase.sb.Tuple;
import com.tibco.ep.samples.javafunction.UtilFunctions;
import com.tibco.ep.testing.framework.TransactionalDeadlockDetectedException;
import com.tibco.ep.testing.framework.TransactionalMemoryLeakException;
import com.tibco.ep.testing.framework.UnitTest;

/**
 * Unit tests for java function
 *
 */
public class UtilFunctionsTest extends UnitTest {

    /**
     * Setup test framework before running tests
     * 
     */
    @Before
    public void initializeTest()  {
        this.initialize();
    }

    /**
     * Complete test framework and check for any errors
     * 
     * @throws TransactionalMemoryLeakException Leak detected
     * @throws TransactionalDeadlockDetectedException Deadlock detected
     */
    @After
    public void completeTest() throws TransactionalMemoryLeakException, TransactionalDeadlockDetectedException {
        this.complete();
    }

    /**
     * Test null list
     */
    @Test
    public void testNullList() {
        List<Tuple> t = null;

        assertNull(UtilFunctions.Density(t));
    }

    /**
     * Test zero szie
     */
    @Test
    public void testZeroSize() {
        List<Tuple> t = new ArrayList<Tuple>();

        assertNull(UtilFunctions.Density(t));
    }

}
