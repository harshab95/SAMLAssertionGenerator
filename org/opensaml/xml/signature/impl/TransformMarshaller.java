// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.signature.Transform;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class TransformMarshaller extends AbstractXMLSignatureMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final Transform transform = (Transform)xmlObject;
        if (transform.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", transform.getAlgorithm());
        }
    }
}
