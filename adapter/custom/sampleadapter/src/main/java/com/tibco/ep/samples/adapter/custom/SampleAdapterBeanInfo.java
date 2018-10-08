/*******************************************************************************
 *
 * COPYRIGHT
 *      Copyright 2018 TIBCO Software Inc. ALL RIGHTS RESERVED.
 *      TIBCO Software Inc. Confidential Information
 *      
 *******************************************************************************/
package com.tibco.ep.samples.adapter.custom;

import java.beans.*;

import com.streambase.sb.operator.parameter.*;

/**
 * A BeanInfo class controls what properties are exposed, add 
 * metadata about properties (such as which properties are optional), and access 
 * special types of properties that can't be automatically derived via reflection. 
 * If a BeanInfo class is present, only the properties explicitly declared in
 * this class will be exposed by StreamBase.
 */
public class SampleAdapterBeanInfo extends SBSimpleBeanInfo {

    /*
     * The order of properties below determines the order they are displayed within
     * the StreamBase Studio property view. 
     */
    public SBPropertyDescriptor[] getPropertyDescriptorsChecked()
            throws IntrospectionException {
        SBPropertyDescriptor[] p = { new SBPropertyDescriptor("schema0",
                SampleAdapter.class), };
        return p;
    }

}