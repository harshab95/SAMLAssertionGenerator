// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBase64Binary;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSBase64BinaryBuilder extends AbstractXMLObjectBuilder<XSBase64Binary>
{
    @Override
    public XSBase64Binary buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSBase64BinaryImpl(namespaceURI, localName, namespacePrefix);
    }
}
