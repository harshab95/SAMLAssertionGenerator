// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class X509SKIImpl extends XSBase64BinaryImpl implements X509SKI
{
    protected X509SKIImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
