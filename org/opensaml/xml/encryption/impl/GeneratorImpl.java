// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.Generator;
import org.opensaml.xml.signature.impl.CryptoBinaryImpl;

public class GeneratorImpl extends CryptoBinaryImpl implements Generator
{
    protected GeneratorImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
