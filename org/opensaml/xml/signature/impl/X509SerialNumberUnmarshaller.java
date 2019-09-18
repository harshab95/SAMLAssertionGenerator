// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.math.BigInteger;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.signature.X509SerialNumber;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public class X509SerialNumberUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        final X509SerialNumber x509SerialNumber = (X509SerialNumber)xmlObject;
        if (!DatatypeHelper.isEmpty(elementContent)) {
            x509SerialNumber.setValue(new BigInteger(elementContent));
        }
    }
}
