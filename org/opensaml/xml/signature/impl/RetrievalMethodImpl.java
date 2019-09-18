// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.Transforms;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class RetrievalMethodImpl extends AbstractValidatingXMLObject implements RetrievalMethod
{
    private String uri;
    private String type;
    private Transforms transforms;
    
    protected RetrievalMethodImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getURI() {
        return this.uri;
    }
    
    public void setURI(final String newURI) {
        this.uri = this.prepareForAssignment(this.uri, newURI);
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String newType) {
        this.type = this.prepareForAssignment(this.type, newType);
    }
    
    public Transforms getTransforms() {
        return this.transforms;
    }
    
    public void setTransforms(final Transforms newTransforms) {
        this.transforms = this.prepareForAssignment(this.transforms, newTransforms);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.transforms != null) {
            children.add(this.transforms);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
