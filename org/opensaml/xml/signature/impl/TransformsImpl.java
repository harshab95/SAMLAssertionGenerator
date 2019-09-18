// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.signature.Transform;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;
import org.opensaml.xml.signature.Transforms;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class TransformsImpl extends AbstractValidatingXMLObject implements Transforms
{
    private final XMLObjectChildrenList transforms;
    
    protected TransformsImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.transforms = new XMLObjectChildrenList(this);
    }
    
    public List<Transform> getTransforms() {
        return (List<Transform>)this.transforms;
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.transforms);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
