/*******************************************************************************
 *
 * COPYRIGHT
 *      Copyright 2018 TIBCO Software Inc. ALL RIGHTS RESERVED.
 *      TIBCO Software Inc. Confidential Information
 *      
 *******************************************************************************/
package com.tibco.ep.samples.adapter.custom;

import org.slf4j.Logger;

import com.streambase.sb.*;
import com.streambase.sb.adapter.*;
import com.streambase.sb.operator.*;

/**
 *
 * <p>Input adapters take information from outside of an application and
 * provide it in the form of a stream or streams to the application. 
 * Any implementation of this class <b>must</b> extend class InputAdapter.
 * Input adapters should call sendOutput(int, Tuple) to provide tuples 
 * to the application.</p>
 * 
 * <p>For in-depth information on implementing a custom adapter, please
 * see "Developing StreamBase Embedded Adapters" in the StreamBase documentation.</p>
 */
public class SampleAdapter extends InputAdapter implements Parameterizable, Runnable {

    private static final long serialVersionUID = 1444258422850L;
    private Logger logger;
    private Schema schema0;
    private String displayName = "SampleAdapter";

    /**
     * The constructor is called when the Adapter instance is created, but before the Adapter 
     * is connected to the StreamBase application. We recommended that you set the initial input 
     * port and output port count in the constructor by calling setPortHints(inPortCount, outPortCount). 
     * The default is no input ports or output ports. The constructor may also set default values for 
     * adapter parameters. These values will be displayed in StreamBase Studio when a new adapter is 
     * dragged to the canvas, and serve as the default values for omitted optional parameters.
     */
    public SampleAdapter() {
        super();
        logger = getLogger();
        setPortHints(0, 1);
        setDisplayName(displayName);
        setShortDisplayName(this.getClass().getSimpleName());
    }

    /**
     * Typecheck this adapter. The typecheck method is called after the adapter instance is
     * constructed, and once input ports are connected. The adapter should validate its parameters 
     * and throw PropertyTypecheckException (or TypecheckException) if any problems are found. 
     * The message associated with the thrown exceptions will be displayed in StreamBase Studio during authoring,
     * or printed on the console by sbd. Input adapters should set the schema of each output
     * port by calling the setOutputSchema(portNum, schema) method for each output port. 
     * If the adapter needs to change the number of input ports based on parameter values,
     * it should call requireInputPortCount(portCount) at this point. 
     */
    public void typecheck() throws TypecheckException {
        // TODO Make sure all properties have valid values.
        // Sets the output schema for the given output port (port #'s are zero based).
        setOutputSchema(0, schema0);
    }

    /**
     * Initialize the adapter. If typecheck succeeds, the init method is called before
     * the StreamBase application is started. Note that your adapter is not required to
     * define the init method, unless you need to register a runnable or perform
     * initialization of a resource such as, for example, a JDBC pool.
     */
    public void init() throws StreamBaseException {
        super.init();

        // Register the object so it will be run as a thread managed by StreamBase.
        registerRunnable(this, true);
    }

    /**
     * Shutdown adapter by cleaning up any resources used (e.g. close an output file
     * if it has been opened). When the application is shutting down, the adapter's shutdown.
     * method will be called first. Once this has returned, all threads should exit and the
     * adapter is considered shutdown.
     */
    public void shutdown() {

    }

    /**
     * Main thread of the adapter. At this point, the application begins to run.
     * StreamBase will start threads for any managed runnables registered by
     * earlier calls to registerRunnable. Input adapters can call sendOutput(port, tuple) 
     * at any time to output a tuple to the specified port.
     */
    public void run() {
        // fill the tuple and output the tuple
        fillAndOutputTuple();
    }

    private void fillAndOutputTuple() {
        if (logger.isInfoEnabled()) {
            logger.info("fill the tuple and output the tuple...");
        }
        try {
            while (shouldRun()) {
                int portCount = getOutputPortCount();
                for (int i = 0; i < portCount; i++) {
                    // TODO Get output schema and create tuple from the schema
                    Tuple tuple = getOutputSchema(i).createTuple();

                    // TODO Accept input from other resources

                    // TODO set the tuple fields as desired based on accepted input

                    try {
                        // Output the tuple

                        sendOutput(i, tuple);
                    } catch (StreamBaseException e) {
                        System.err.println("Exception sending output to port"
                                + portCount);
                        System.err.println(e);
                        return;
                    }
                    try {
                        // TODO this sleep is only here to throttle this simulated data source.
                        // Real data sources are likely not to need any throttling as the StreamBase
                        // Server is almost always capable of higher input rates than remote data
                        // sources can output, and buffering is managed by the Server if needed.
                        logger.info("Sleeping 250ms");
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                    }
                }
            }
        } finally {
            try {
                // TODO Be sure to close the resource
            } catch (Exception e) {
            }
        }
    }

    /***************************************************************************************
     * The getter and setter methods provided by the Parameterizable object.               *
     * StreamBase Studio uses them to determine the name and type of each property         *
     * and obviously, to set and get the property values.                                  *
     ***************************************************************************************/

    /**
     * Get schema
     * @return schema
     */
    public Schema getSchema0() {
        return this.schema0;
    }

    /**
     * Set schema
     * @param schema0 schema
     */
    public void setSchema0(Schema schema0) {
        this.schema0 = schema0;
    }

}
