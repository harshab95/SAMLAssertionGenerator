// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import java.util.Collections;
import java.util.LinkedList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.schema.XSQName;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSQNameImpl extends AbstractValidatingXMLObject implements XSQName
{
    private QName value;
    
    protected XSQNameImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public QName getValue() {
        return this.value;
    }
    
    public void setValue(final QName newValue) {
        this.value = this.prepareElementContentForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList((List<? extends XMLObject>)new LinkedList<XMLObject>());
    }
}
