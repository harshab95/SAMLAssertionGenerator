// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.PGPKeyID;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class PGPKeyIDImpl extends XSBase64BinaryImpl implements PGPKeyID
{
    protected PGPKeyIDImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
