// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.Seed;
import org.opensaml.xml.signature.impl.CryptoBinaryImpl;

public class SeedImpl extends CryptoBinaryImpl implements Seed
{
    protected SeedImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
