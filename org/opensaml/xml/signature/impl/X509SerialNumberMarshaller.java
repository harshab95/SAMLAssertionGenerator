// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;

public class X509SerialNumberMarshaller extends AbstractXMLObjectMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final X509SerialNumber x509SerialNumber = (X509SerialNumber)xmlObject;
        if (x509SerialNumber.getValue() != null) {
            XMLHelper.appendTextContent(domElement, x509SerialNumber.getValue().toString());
        }
    }
}
