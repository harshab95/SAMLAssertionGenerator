// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Exponent;
import org.opensaml.xml.signature.Modulus;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.XMLObject;

public class RSAKeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final RSAKeyValue keyValue = (RSAKeyValue)parentXMLObject;
        if (childXMLObject instanceof Modulus) {
            keyValue.setModulus((Modulus)childXMLObject);
        }
        else if (childXMLObject instanceof Exponent) {
            keyValue.setExponent((Exponent)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
