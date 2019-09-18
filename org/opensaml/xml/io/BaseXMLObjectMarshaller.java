// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public abstract class BaseXMLObjectMarshaller extends AbstractXMLObjectMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
}
