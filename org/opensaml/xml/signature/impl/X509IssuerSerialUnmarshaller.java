// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.signature.X509IssuerName;
import org.opensaml.xml.signature.X509IssuerSerial;
import org.opensaml.xml.XMLObject;

public class X509IssuerSerialUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final X509IssuerSerial keyValue = (X509IssuerSerial)parentXMLObject;
        if (childXMLObject instanceof X509IssuerName) {
            keyValue.setX509IssuerName((X509IssuerName)childXMLObject);
        }
        else if (childXMLObject instanceof X509SerialNumber) {
            keyValue.setX509SerialNumber((X509SerialNumber)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
