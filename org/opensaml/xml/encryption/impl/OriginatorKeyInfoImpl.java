// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.OriginatorKeyInfo;
import org.opensaml.xml.signature.impl.KeyInfoTypeImpl;

public class OriginatorKeyInfoImpl extends KeyInfoTypeImpl implements OriginatorKeyInfo
{
    protected OriginatorKeyInfoImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
