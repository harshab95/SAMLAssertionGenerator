// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSStringImpl extends AbstractValidatingXMLObject implements XSString
{
    private String value;
    
    protected XSStringImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}
