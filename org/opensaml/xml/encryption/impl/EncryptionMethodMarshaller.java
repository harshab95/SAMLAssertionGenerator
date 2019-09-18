// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class EncryptionMethodMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final EncryptionMethod em = (EncryptionMethod)xmlObject;
        if (em.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", em.getAlgorithm());
        }
    }
}
