// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.XMLObject;

public class KeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final KeyValue keyValue = (KeyValue)parentXMLObject;
        if (childXMLObject instanceof DSAKeyValue) {
            keyValue.setDSAKeyValue((DSAKeyValue)childXMLObject);
        }
        else if (childXMLObject instanceof RSAKeyValue) {
            keyValue.setRSAKeyValue((RSAKeyValue)childXMLObject);
        }
        else if (keyValue.getUnknownXMLObject() == null) {
            keyValue.setUnknownXMLObject(childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
