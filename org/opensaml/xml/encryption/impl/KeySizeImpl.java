// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.KeySize;
import org.opensaml.xml.schema.impl.XSIntegerImpl;

public class KeySizeImpl extends XSIntegerImpl implements KeySize
{
    protected KeySizeImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
