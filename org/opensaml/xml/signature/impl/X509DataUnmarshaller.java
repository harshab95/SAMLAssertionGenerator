// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.XMLObject;

public class X509DataUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final X509Data x509Data = (X509Data)parentXMLObject;
        x509Data.getXMLObjects().add(childXMLObject);
    }
}
