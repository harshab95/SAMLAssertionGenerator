// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.signature.X509IssuerName;
import org.opensaml.xml.signature.X509IssuerSerial;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class X509IssuerSerialImpl extends AbstractValidatingXMLObject implements X509IssuerSerial
{
    private X509IssuerName issuerName;
    private X509SerialNumber serialNumber;
    
    protected X509IssuerSerialImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public X509IssuerName getX509IssuerName() {
        return this.issuerName;
    }
    
    public void setX509IssuerName(final X509IssuerName newX509IssuerName) {
        this.issuerName = this.prepareForAssignment(this.issuerName, newX509IssuerName);
    }
    
    public X509SerialNumber getX509SerialNumber() {
        return this.serialNumber;
    }
    
    public void setX509SerialNumber(final X509SerialNumber newX509SerialNumber) {
        this.serialNumber = this.prepareForAssignment(this.serialNumber, newX509SerialNumber);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.issuerName != null) {
            children.add(this.issuerName);
        }
        if (this.serialNumber != null) {
            children.add(this.serialNumber);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
