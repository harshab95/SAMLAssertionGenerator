// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.schema.XSInteger;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public class XSIntegerUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        final XSInteger xsiInteger = (XSInteger)xmlObject;
        if (elementContent != null) {
            xsiInteger.setValue(Integer.valueOf(elementContent.trim()));
        }
    }
}
