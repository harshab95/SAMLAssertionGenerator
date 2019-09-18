// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.encryption.Transforms;
import org.opensaml.xml.encryption.CipherReference;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class CipherReferenceImpl extends AbstractValidatingXMLObject implements CipherReference
{
    private String uri;
    private Transforms transforms;
    
    protected CipherReferenceImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getURI() {
        return this.uri;
    }
    
    public void setURI(final String newURI) {
        this.uri = this.prepareForAssignment(this.uri, newURI);
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
