// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.opensaml.xml.util.XMLHelper;
import javax.xml.namespace.QName;
import java.util.Map;
import org.w3c.dom.Element;

public class AbstractExtensibleXMLObjectMarshaller extends AbstractElementExtensibleXMLObjectMarshaller
{
    public AbstractExtensibleXMLObjectMarshaller() {
    }
    
    @Deprecated
    public AbstractExtensibleXMLObjectMarshaller(final String targetNamespaceURI, final String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }
    
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
        final Document document = domElement.getOwnerDocument();
        for (final Map.Entry<QName, String> entry : anyAttribute.getUnknownAttributes().entrySet()) {
            final Attr attribute = XMLHelper.constructAttribute(document, entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (Configuration.isIDAttribute(entry.getKey()) || anyAttribute.getUnknownAttributes().isIDAttribute(entry.getKey())) {
                attribute.getOwnerElement().setIdAttributeNode(attribute, true);
            }
        }
    }
}
