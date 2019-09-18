// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.KeySize;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KeySizeBuilder extends AbstractXMLObjectBuilder<KeySize> implements XMLEncryptionBuilder<KeySize>
{
    @Override
    public KeySize buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KeySizeImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KeySize buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeySize", "xenc");
    }
}
