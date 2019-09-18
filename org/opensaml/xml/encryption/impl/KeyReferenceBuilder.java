// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.KeyReference;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KeyReferenceBuilder extends AbstractXMLObjectBuilder<KeyReference> implements XMLEncryptionBuilder<KeyReference>
{
    @Override
    public KeyReference buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KeyReferenceImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KeyReference buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeyReference", "xenc");
    }
}
