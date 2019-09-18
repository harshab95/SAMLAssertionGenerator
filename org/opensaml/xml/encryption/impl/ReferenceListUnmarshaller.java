// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.KeyReference;
import org.opensaml.xml.encryption.DataReference;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.XMLObject;

public class ReferenceListUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final ReferenceList rl = (ReferenceList)parentXMLObject;
        if (childXMLObject instanceof DataReference) {
            rl.getReferences().add((DataReference)childXMLObject);
        }
        else if (childXMLObject instanceof KeyReference) {
            rl.getReferences().add((KeyReference)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
