// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.AgreementMethod;
import org.opensaml.xml.signature.MgmtData;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.signature.KeyName;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.signature.KeyInfoType;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class KeyInfoTypeImpl extends AbstractValidatingXMLObject implements KeyInfoType
{
    private final IndexedXMLObjectChildrenList indexedChildren;
    private String id;
    
    protected KeyInfoTypeImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.indexedChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String newID) {
        final String oldID = this.id;
        this.registerOwnID(oldID, this.id = this.prepareForAssignment(this.id, newID));
    }
    
    public List<XMLObject> getXMLObjects() {
        return (List<XMLObject>)this.indexedChildren;
    }
    
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.indexedChildren.subList(typeOrName);
    }
    
    public List<KeyName> getKeyNames() {
        return (List<KeyName>)this.indexedChildren.subList(KeyName.DEFAULT_ELEMENT_NAME);
    }
    
    public List<KeyValue> getKeyValues() {
        return (List<KeyValue>)this.indexedChildren.subList(KeyValue.DEFAULT_ELEMENT_NAME);
    }
    
    public List<RetrievalMethod> getRetrievalMethods() {
        return (List<RetrievalMethod>)this.indexedChildren.subList(RetrievalMethod.DEFAULT_ELEMENT_NAME);
    }
    
    public List<X509Data> getX509Datas() {
        return (List<X509Data>)this.indexedChildren.subList(X509Data.DEFAULT_ELEMENT_NAME);
    }
    
    public List<PGPData> getPGPDatas() {
        return (List<PGPData>)this.indexedChildren.subList(PGPData.DEFAULT_ELEMENT_NAME);
    }
    
    public List<SPKIData> getSPKIDatas() {
        return (List<SPKIData>)this.indexedChildren.subList(SPKIData.DEFAULT_ELEMENT_NAME);
    }
    
    public List<MgmtData> getMgmtDatas() {
        return (List<MgmtData>)this.indexedChildren.subList(MgmtData.DEFAULT_ELEMENT_NAME);
    }
    
    public List<AgreementMethod> getAgreementMethods() {
        return (List<AgreementMethod>)this.indexedChildren.subList(AgreementMethod.DEFAULT_ELEMENT_NAME);
    }
    
    public List<EncryptedKey> getEncryptedKeys() {
        return (List<EncryptedKey>)this.indexedChildren.subList(EncryptedKey.DEFAULT_ELEMENT_NAME);
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
