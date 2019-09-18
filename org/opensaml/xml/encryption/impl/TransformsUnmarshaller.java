// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Transform;
import org.opensaml.xml.encryption.Transforms;
import org.opensaml.xml.XMLObject;

public class TransformsUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final Transforms transforms = (Transforms)parentXMLObject;
        if (childXMLObject instanceof Transform) {
            transforms.getTransforms().add((Transform)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
