// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.ReferenceType;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class ReferenceTypeMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final ReferenceType rt = (ReferenceType)xmlObject;
        if (rt.getURI() != null) {
            domElement.setAttributeNS(null, "URI", rt.getURI());
        }
        else {
            super.marshallAttributes(xmlObject, domElement);
        }
    }
}
