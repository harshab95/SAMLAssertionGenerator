// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSQName;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSQNameBuilder extends AbstractXMLObjectBuilder<XSQName>
{
    @Override
    public XSQName buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSQNameImpl(namespaceURI, localName, namespacePrefix);
    }
}
