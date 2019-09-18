// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public abstract class BaseXMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
    }
}
