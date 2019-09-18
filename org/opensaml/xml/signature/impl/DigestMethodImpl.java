// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.signature.DigestMethod;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class DigestMethodImpl extends AbstractValidatingXMLObject implements DigestMethod
{
    private String algorithm;
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren;
    
    protected DigestMethodImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownChildren = new IndexedXMLObjectChildrenList<XMLObject>(this);
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public void setAlgorithm(final String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.unknownChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.unknownChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
