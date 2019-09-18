// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class EncryptedKeyBuilder extends AbstractXMLObjectBuilder<EncryptedKey> implements XMLEncryptionBuilder<EncryptedKey>
{
    public EncryptedKey buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
    }
    
    @Override
    public EncryptedKey buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new EncryptedKeyImpl(namespaceURI, localName, namespacePrefix);
    }
}
