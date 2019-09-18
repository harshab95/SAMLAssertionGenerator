// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.signature.SPKISexp;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class SPKIDataImpl extends AbstractValidatingXMLObject implements SPKIData
{
    private final IndexedXMLObjectChildrenList indexedChildren;
    
    protected SPKIDataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.indexedChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public List<XMLObject> getXMLObjects() {
        return (List<XMLObject>)this.indexedChildren;
    }
    
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.indexedChildren.subList(typeOrName);
    }
    
    public List<SPKISexp> getSPKISexps() {
        return (List<SPKISexp>)this.indexedChildren.subList(SPKISexp.DEFAULT_ELEMENT_NAME);
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
