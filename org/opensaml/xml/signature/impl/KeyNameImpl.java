// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class KeyNameImpl extends XSStringImpl implements KeyName
{
    protected KeyNameImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
