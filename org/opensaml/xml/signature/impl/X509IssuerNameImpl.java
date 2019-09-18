// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.X509IssuerName;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class X509IssuerNameImpl extends XSStringImpl implements X509IssuerName
{
    protected X509IssuerNameImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
