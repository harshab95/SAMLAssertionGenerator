// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.PgenCounter;
import org.opensaml.xml.signature.impl.CryptoBinaryImpl;

public class PgenCounterImpl extends CryptoBinaryImpl implements PgenCounter
{
    protected PgenCounterImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
