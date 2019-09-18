// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.RecipientKeyInfo;
import org.opensaml.xml.encryption.OriginatorKeyInfo;
import org.opensaml.xml.encryption.KANonce;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.AgreementMethod;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class AgreementMethodUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final AgreementMethod am = (AgreementMethod)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            am.setAlgorithm(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final AgreementMethod am = (AgreementMethod)parentXMLObject;
        if (childXMLObject instanceof KANonce) {
            am.setKANonce((KANonce)childXMLObject);
        }
        else if (childXMLObject instanceof OriginatorKeyInfo) {
            am.setOriginatorKeyInfo((OriginatorKeyInfo)childXMLObject);
        }
        else if (childXMLObject instanceof RecipientKeyInfo) {
            am.setRecipientKeyInfo((RecipientKeyInfo)childXMLObject);
        }
        else {
            am.getUnknownXMLObjects().add(childXMLObject);
        }
    }
}
