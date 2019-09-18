// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import org.opensaml.xml.XMLObject;
import java.util.LinkedList;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.xml.signature.ContentReference;
import java.util.List;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.AbstractXMLObject;

public class SignatureImpl extends AbstractXMLObject implements Signature
{
    private String canonicalizationAlgorithm;
    private String signatureAlgorithm;
    private Integer hmacOutputLength;
    private Credential signingCredential;
    private KeyInfo keyInfo;
    private List<ContentReference> contentReferences;
    private XMLSignature xmlSignature;
    
    protected SignatureImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.contentReferences = new LinkedList<ContentReference>();
    }
    
    public String getCanonicalizationAlgorithm() {
        return this.canonicalizationAlgorithm;
    }
    
    public void setCanonicalizationAlgorithm(final String newAlgorithm) {
        this.canonicalizationAlgorithm = this.prepareForAssignment(this.canonicalizationAlgorithm, newAlgorithm);
    }
    
    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }
    
    public void setSignatureAlgorithm(final String newAlgorithm) {
        this.signatureAlgorithm = this.prepareForAssignment(this.signatureAlgorithm, newAlgorithm);
    }
    
    public Integer getHMACOutputLength() {
        return this.hmacOutputLength;
    }
    
    public void setHMACOutputLength(final Integer length) {
        this.hmacOutputLength = this.prepareForAssignment(this.hmacOutputLength, length);
    }
    
    public Credential getSigningCredential() {
        return this.signingCredential;
    }
    
    public void setSigningCredential(final Credential newCredential) {
        this.signingCredential = this.prepareForAssignment(this.signingCredential, newCredential);
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
    
    public void setKeyInfo(final KeyInfo newKeyInfo) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, newKeyInfo);
    }
    
    public List<ContentReference> getContentReferences() {
        return this.contentReferences;
    }
    
    public List<XMLObject> getOrderedChildren() {
        return (List<XMLObject>)Collections.EMPTY_LIST;
    }
    
    @Override
    public void releaseDOM() {
        super.releaseDOM();
        this.xmlSignature = null;
        if (this.keyInfo != null) {
            this.keyInfo.releaseChildrenDOM(true);
            this.keyInfo.releaseDOM();
        }
    }
    
    public XMLSignature getXMLSignature() {
        return this.xmlSignature;
    }
    
    public void setXMLSignature(final XMLSignature signature) {
        this.xmlSignature = this.prepareForAssignment(this.xmlSignature, signature);
    }
}
