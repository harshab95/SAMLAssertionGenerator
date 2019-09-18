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
import org.opensaml.xml.encryption.RecipientKeyInfo;
import org.opensaml.xml.encryption.OriginatorKeyInfo;
import org.opensaml.xml.encryption.KANonce;
import org.opensaml.xml.encryption.AgreementMethod;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class AgreementMethodImpl extends AbstractValidatingXMLObject implements AgreementMethod
{
    private String algorithm;
    private KANonce kaNonce;
    private OriginatorKeyInfo originatorKeyInfo;
    private RecipientKeyInfo recipientKeyInfo;
    private IndexedXMLObjectChildrenList xmlChildren;
    
    protected AgreementMethodImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.xmlChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public void setAlgorithm(final String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }
    
    public KANonce getKANonce() {
        return this.kaNonce;
    }
    
    public void setKANonce(final KANonce newKANonce) {
        this.kaNonce = this.prepareForAssignment(this.kaNonce, newKANonce);
    }
    
    public OriginatorKeyInfo getOriginatorKeyInfo() {
        return this.originatorKeyInfo;
    }
    
    public void setOriginatorKeyInfo(final OriginatorKeyInfo newOriginatorKeyInfo) {
        this.originatorKeyInfo = this.prepareForAssignment(this.originatorKeyInfo, newOriginatorKeyInfo);
    }
    
    public RecipientKeyInfo getRecipientKeyInfo() {
        return this.recipientKeyInfo;
    }
    
    public void setRecipientKeyInfo(final RecipientKeyInfo newRecipientKeyInfo) {
        this.recipientKeyInfo = this.prepareForAssignment(this.recipientKeyInfo, newRecipientKeyInfo);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return (List<XMLObject>)this.xmlChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.xmlChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.kaNonce != null) {
            children.add(this.kaNonce);
        }
        children.addAll(this.xmlChildren);
        if (this.originatorKeyInfo != null) {
            children.add(this.originatorKeyInfo);
        }
        if (this.recipientKeyInfo != null) {
            children.add(this.recipientKeyInfo);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
