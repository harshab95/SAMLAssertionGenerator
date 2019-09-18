// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import java.util.Iterator;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.util.XMLHelper;
import javax.xml.namespace.QName;
import java.util.Map;
import org.opensaml.xml.encryption.EncryptionProperty;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class EncryptionPropertyMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final EncryptionProperty ep = (EncryptionProperty)xmlObject;
        if (ep.getID() != null) {
            domElement.setAttributeNS(null, "Id", ep.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
        if (ep.getTarget() != null) {
            domElement.setAttributeNS(null, "Target", ep.getTarget());
        }
        for (final Map.Entry<QName, String> entry : ep.getUnknownAttributes().entrySet()) {
            final Attr attribute = XMLHelper.constructAttribute(domElement.getOwnerDocument(), entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (Configuration.isIDAttribute(entry.getKey()) || ep.getUnknownAttributes().isIDAttribute(entry.getKey())) {
                attribute.getOwnerElement().setIdAttributeNode(attribute, true);
            }
        }
    }
}
