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
import org.opensaml.xml.signature.PGPKeyPacket;
import org.opensaml.xml.signature.PGPKeyID;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class PGPDataImpl extends AbstractValidatingXMLObject implements PGPData
{
    private PGPKeyID pgpKeyID;
    private PGPKeyPacket pgpKeyPacket;
    private final IndexedXMLObjectChildrenList xmlChildren;
    
    protected PGPDataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.xmlChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public PGPKeyID getPGPKeyID() {
        return this.pgpKeyID;
    }
    
    public void setPGPKeyID(final PGPKeyID newPGPKeyID) {
        this.pgpKeyID = this.prepareForAssignment(this.pgpKeyID, newPGPKeyID);
    }
    
    public PGPKeyPacket getPGPKeyPacket() {
        return this.pgpKeyPacket;
    }
    
    public void setPGPKeyPacket(final PGPKeyPacket newPGPKeyPacket) {
        this.pgpKeyPacket = this.prepareForAssignment(this.pgpKeyPacket, newPGPKeyPacket);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return (List<XMLObject>)this.xmlChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.xmlChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.pgpKeyID != null) {
            children.add(this.pgpKeyID);
        }
        if (this.pgpKeyPacket != null) {
            children.add(this.pgpKeyPacket);
        }
        children.addAll(this.xmlChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
