// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.XPath;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class XPathImpl extends XSStringImpl implements XPath
{
    protected XPathImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
