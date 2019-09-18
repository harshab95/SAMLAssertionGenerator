// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSAnyBuilder extends AbstractXMLObjectBuilder<XSAny>
{
    @Override
    public XSAny buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSAnyImpl(namespaceURI, localName, namespacePrefix);
    }
}
