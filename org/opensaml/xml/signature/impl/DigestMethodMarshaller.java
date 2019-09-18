// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.signature.DigestMethod;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class DigestMethodMarshaller extends AbstractXMLSignatureMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final DigestMethod dm = (DigestMethod)xmlObject;
        if (dm.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", dm.getAlgorithm());
        }
    }
}
