// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSIntegerBuilder extends AbstractXMLObjectBuilder<XSInteger>
{
    @Override
    public XSInteger buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSIntegerImpl(namespaceURI, localName, namespacePrefix);
    }
}
