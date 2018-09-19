package com.tibco.ep.samples.nativelibrary.narcpplib;

/**
 * c++ utility functions
 */
public class CallCpp {

    static {
        //
        // Don't need the native part when we're typechecking in studio
        //
        if (System.getProperty("streambase.ide.is-running") == null) {
            System.loadLibrary("nar-cpplib-1.0.0");
            System.out.println("loaded shared lib");
        }
    }


    /**
     * Set the static int in C++ land to some value
     * @param val Value
     * 
     * @return the old value
     */
    public native static int setCppInt(int val);

    /**
     * Increment the int in C++ by the arg.
     * @param val Value
     * 
     * @return the new value
     */
    public native static int incrementCppInt(int val);

}
