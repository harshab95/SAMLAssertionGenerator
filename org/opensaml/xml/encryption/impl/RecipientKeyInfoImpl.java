// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.RecipientKeyInfo;
import org.opensaml.xml.signature.impl.KeyInfoTypeImpl;

public class RecipientKeyInfoImpl extends KeyInfoTypeImpl implements RecipientKeyInfo
{
    protected RecipientKeyInfoImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
