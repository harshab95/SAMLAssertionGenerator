// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.schema.XSBase64Binary;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;

public class XSBase64BinaryMarshaller extends AbstractXMLObjectMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final XSBase64Binary xsBase64Binary = (XSBase64Binary)xmlObject;
        XMLHelper.appendTextContent(domElement, xsBase64Binary.getValue());
    }
}
