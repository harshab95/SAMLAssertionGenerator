// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import java.util.List;
import java.math.BigInteger;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class X509SerialNumberImpl extends AbstractValidatingXMLObject implements X509SerialNumber
{
    private BigInteger value;
    
    protected X509SerialNumberImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public BigInteger getValue() {
        return this.value;
    }
    
    public void setValue(final BigInteger newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}
