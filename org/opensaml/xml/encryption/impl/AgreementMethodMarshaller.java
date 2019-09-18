// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.AgreementMethod;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class AgreementMethodMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final AgreementMethod am = (AgreementMethod)xmlObject;
        if (am.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", am.getAlgorithm());
        }
    }
}
