// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.signature.X509CRL;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509SubjectName;
import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.signature.X509IssuerSerial;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class X509DataImpl extends AbstractValidatingXMLObject implements X509Data
{
    private final IndexedXMLObjectChildrenList indexedChildren;
    
    protected X509DataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.indexedChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public List<XMLObject> getXMLObjects() {
        return (List<XMLObject>)this.indexedChildren;
    }
    
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.indexedChildren.subList(typeOrName);
    }
    
    public List<X509IssuerSerial> getX509IssuerSerials() {
        return (List<X509IssuerSerial>)this.indexedChildren.subList(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
    }
    
    public List<X509SKI> getX509SKIs() {
        return (List<X509SKI>)this.indexedChildren.subList(X509SKI.DEFAULT_ELEMENT_NAME);
    }
    
    public List<X509SubjectName> getX509SubjectNames() {
        return (List<X509SubjectName>)this.indexedChildren.subList(X509SubjectName.DEFAULT_ELEMENT_NAME);
    }
    
    public List<X509Certificate> getX509Certificates() {
        return (List<X509Certificate>)this.indexedChildren.subList(X509Certificate.DEFAULT_ELEMENT_NAME);
    }
    
    public List<X509CRL> getX509CRLs() {
        return (List<X509CRL>)this.indexedChildren.subList(X509CRL.DEFAULT_ELEMENT_NAME);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.indexedChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
