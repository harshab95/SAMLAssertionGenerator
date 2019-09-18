// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.encryption.OAEPparams;
import org.opensaml.xml.encryption.KeySize;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class EncryptionMethodImpl extends AbstractValidatingXMLObject implements EncryptionMethod
{
    private String algorithm;
    private KeySize keySize;
    private OAEPparams oaepParams;
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren;
    
    protected EncryptionMethodImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownChildren = new IndexedXMLObjectChildrenList<XMLObject>(this);
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public void setAlgorithm(final String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }
    
    public KeySize getKeySize() {
        return this.keySize;
    }
    
    public void setKeySize(final KeySize newKeySize) {
        this.keySize = this.prepareForAssignment(this.keySize, newKeySize);
    }
    
    public OAEPparams getOAEPparams() {
        return this.oaepParams;
    }
    
    public void setOAEPparams(final OAEPparams newOAEPparams) {
        this.oaepParams = this.prepareForAssignment(this.oaepParams, newOAEPparams);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.unknownChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.keySize != null) {
            children.add(this.keySize);
        }
        if (this.oaepParams != null) {
            children.add(this.oaepParams);
        }
        children.addAll(this.unknownChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
