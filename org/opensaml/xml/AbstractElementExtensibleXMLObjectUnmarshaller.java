// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public abstract class AbstractElementExtensibleXMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    public AbstractElementExtensibleXMLObjectUnmarshaller() {
    }
    
    @Deprecated
    public AbstractElementExtensibleXMLObjectUnmarshaller(final String targetNamespaceURI, final String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final ElementExtensibleXMLObject any = (ElementExtensibleXMLObject)parentXMLObject;
        any.getUnknownXMLObjects().add(childXMLObject);
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
    }
}
