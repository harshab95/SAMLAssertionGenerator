// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.Public;
import org.opensaml.xml.signature.impl.CryptoBinaryImpl;

public class PublicImpl extends CryptoBinaryImpl implements Public
{
    protected PublicImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
