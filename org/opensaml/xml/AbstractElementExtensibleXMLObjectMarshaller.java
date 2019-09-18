// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;

public abstract class AbstractElementExtensibleXMLObjectMarshaller extends AbstractXMLObjectMarshaller
{
    public AbstractElementExtensibleXMLObjectMarshaller() {
    }
    
    @Deprecated
    protected AbstractElementExtensibleXMLObjectMarshaller(final String targetNamespaceURI, final String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }
    
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
}
