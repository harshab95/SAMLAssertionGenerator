// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.signature.RetrievalMethod;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class RetrievalMethodMarshaller extends AbstractXMLSignatureMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final RetrievalMethod rm = (RetrievalMethod)xmlObject;
        if (rm.getURI() != null) {
            domElement.setAttributeNS(null, "URI", rm.getURI());
        }
        if (rm.getType() != null) {
            domElement.setAttributeNS(null, "Type", rm.getType());
        }
    }
}
