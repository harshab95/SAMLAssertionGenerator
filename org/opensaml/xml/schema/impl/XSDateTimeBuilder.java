// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSDateTime;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSDateTimeBuilder extends AbstractXMLObjectBuilder<XSDateTime>
{
    @Override
    public XSDateTime buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSDateTimeImpl(namespaceURI, localName, namespacePrefix);
    }
}
