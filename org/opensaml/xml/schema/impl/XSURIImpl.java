// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import java.util.Collections;
import java.util.LinkedList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.schema.XSURI;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSURIImpl extends AbstractValidatingXMLObject implements XSURI
{
    private String value;
    
    protected XSURIImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList((List<? extends XMLObject>)new LinkedList<XMLObject>());
    }
}
