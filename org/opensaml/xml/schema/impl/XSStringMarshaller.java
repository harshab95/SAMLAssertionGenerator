// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;

public class XSStringMarshaller extends AbstractXMLObjectMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final XSString xsiString = (XSString)xmlObject;
        XMLHelper.appendTextContent(domElement, xsiString.getValue());
    }
}
