// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.schema.XSBase64Binary;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public class XSBase64BinaryUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        final XSBase64Binary xsBase64Binary = (XSBase64Binary)xmlObject;
        if (elementContent != null) {
            xsBase64Binary.setValue(elementContent.trim());
        }
    }
}
