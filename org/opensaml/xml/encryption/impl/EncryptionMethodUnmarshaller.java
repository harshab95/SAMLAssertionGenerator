// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.OAEPparams;
import org.opensaml.xml.encryption.KeySize;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class EncryptionMethodUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final EncryptionMethod em = (EncryptionMethod)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            em.setAlgorithm(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final EncryptionMethod em = (EncryptionMethod)parentXMLObject;
        if (childXMLObject instanceof KeySize) {
            em.setKeySize((KeySize)childXMLObject);
        }
        else if (childXMLObject instanceof OAEPparams) {
            em.setOAEPparams((OAEPparams)childXMLObject);
        }
        else {
            em.getUnknownXMLObjects().add(childXMLObject);
        }
    }
}
