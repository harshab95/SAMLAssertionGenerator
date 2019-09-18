// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSIntegerImpl extends AbstractValidatingXMLObject implements XSInteger
{
    private Integer value;
    
    protected XSIntegerImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(final Integer newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}
