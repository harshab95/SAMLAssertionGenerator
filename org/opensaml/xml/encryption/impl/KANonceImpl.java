// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.KANonce;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class KANonceImpl extends XSBase64BinaryImpl implements KANonce
{
    protected KANonceImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
