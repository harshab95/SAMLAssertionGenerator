// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.XMLObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public abstract class AbstractXMLSignatureUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    private final Logger log;
    
    public AbstractXMLSignatureUnmarshaller() {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLSignatureUnmarshaller.class);
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        this.log.debug("Ignoring unknown element {}", (Object)childXMLObject.getElementQName());
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        this.log.debug("Ignorning unknown attribute {}", (Object)attribute.getLocalName());
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        this.log.debug("Ignoring element content {}", (Object)elementContent);
    }
}
