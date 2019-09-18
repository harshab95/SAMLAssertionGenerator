// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.CipherReference;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class CipherReferenceMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final CipherReference cr = (CipherReference)xmlObject;
        if (cr.getURI() != null) {
            domElement.setAttributeNS(null, "URI", cr.getURI());
        }
        else {
            super.marshallAttributes(xmlObject, domElement);
        }
    }
}
