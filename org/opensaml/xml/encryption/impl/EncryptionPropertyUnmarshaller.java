// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.encryption.EncryptionProperty;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class EncryptionPropertyUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final EncryptionProperty ep = (EncryptionProperty)xmlObject;
        if (attribute.getLocalName().equals("Id")) {
            ep.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
        else if (attribute.getLocalName().equals("Target")) {
            ep.setTarget(attribute.getValue());
        }
        else {
            final QName attributeName = XMLHelper.getNodeQName(attribute);
            if (attribute.isId()) {
                ep.getUnknownAttributes().registerID(attributeName);
            }
            ep.getUnknownAttributes().put(attributeName, attribute.getValue());
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final EncryptionProperty ep = (EncryptionProperty)parentXMLObject;
        ep.getUnknownXMLObjects().add(childXMLObject);
    }
}
