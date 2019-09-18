// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.opensaml.xml.encryption.EncryptedType;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public abstract class EncryptedTypeImpl extends AbstractValidatingXMLObject implements EncryptedType
{
    private String id;
    private String type;
    private String mimeType;
    private String encoding;
    private EncryptionMethod encryptionMethod;
    private KeyInfo keyInfo;
    private CipherData cipherData;
    private EncryptionProperties encryptionProperties;
    
    protected EncryptedTypeImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String newID) {
        final String oldID = this.id;
        this.registerOwnID(oldID, this.id = this.prepareForAssignment(this.id, newID));
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String newType) {
        this.type = this.prepareForAssignment(this.type, newType);
    }
    
    public String getMimeType() {
        return this.mimeType;
    }
    
    public void setMimeType(final String newMimeType) {
        this.mimeType = this.prepareForAssignment(this.mimeType, newMimeType);
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void setEncoding(final String newEncoding) {
        this.encoding = this.prepareForAssignment(this.encoding, newEncoding);
    }
    
    public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
    }
    
    public void setEncryptionMethod(final EncryptionMethod newEncryptionMethod) {
        this.encryptionMethod = this.prepareForAssignment(this.encryptionMethod, newEncryptionMethod);
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
    
    public void setKeyInfo(final KeyInfo newKeyInfo) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, newKeyInfo);
    }
    
    public CipherData getCipherData() {
        return this.cipherData;
    }
    
    public void setCipherData(final CipherData newCipherData) {
        this.cipherData = this.prepareForAssignment(this.cipherData, newCipherData);
    }
    
    public EncryptionProperties getEncryptionProperties() {
        return this.encryptionProperties;
    }
    
    public void setEncryptionProperties(final EncryptionProperties newEncryptionProperties) {
        this.encryptionProperties = this.prepareForAssignment(this.encryptionProperties, newEncryptionProperties);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.encryptionMethod != null) {
            children.add(this.encryptionMethod);
        }
        if (this.keyInfo != null) {
            children.add(this.keyInfo);
        }
        if (this.cipherData != null) {
            children.add(this.cipherData);
        }
        if (this.encryptionProperties != null) {
            children.add(this.encryptionProperties);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
