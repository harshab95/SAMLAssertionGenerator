// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.CipherValue;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class CipherValueImpl extends XSBase64BinaryImpl implements CipherValue
{
    protected CipherValueImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
