// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.util.AttributeMap;

public abstract class AbstractExtensibleXMLObject extends AbstractElementExtensibleXMLObject implements AttributeExtensibleXMLObject, ElementExtensibleXMLObject
{
    private AttributeMap anyAttributes;
    
    public AbstractExtensibleXMLObject(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.anyAttributes = new AttributeMap(this);
    }
    
    public AttributeMap getUnknownAttributes() {
        return this.anyAttributes;
    }
}
