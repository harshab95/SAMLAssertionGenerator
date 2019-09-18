// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.CipherReference;
import org.opensaml.xml.encryption.CipherValue;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.XMLObject;

public class CipherDataUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final CipherData cipherData = (CipherData)parentXMLObject;
        if (childXMLObject instanceof CipherValue) {
            cipherData.setCipherValue((CipherValue)childXMLObject);
        }
        else if (childXMLObject instanceof CipherReference) {
            cipherData.setCipherReference((CipherReference)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
