// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.signature.XPath;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.signature.Transform;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class TransformImpl extends AbstractValidatingXMLObject implements Transform
{
    private String algorithm;
    private final IndexedXMLObjectChildrenList indexedChildren;
    
    protected TransformImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.indexedChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public void setAlgorithm(final String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }
    
    public List<XMLObject> getAllChildren() {
        return (List<XMLObject>)this.indexedChildren;
    }
    
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.indexedChildren.subList(typeOrName);
    }
    
    public List<XPath> getXPaths() {
        return (List<XPath>)this.indexedChildren.subList(XPath.DEFAULT_ELEMENT_NAME);
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
