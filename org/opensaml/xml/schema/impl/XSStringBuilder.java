// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSStringBuilder extends AbstractXMLObjectBuilder<XSString>
{
    @Override
    public XSString buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSStringImpl(namespaceURI, localName, namespacePrefix);
    }
}
