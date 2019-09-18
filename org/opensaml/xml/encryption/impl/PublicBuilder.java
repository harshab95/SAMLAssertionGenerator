// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.Public;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PublicBuilder extends AbstractXMLObjectBuilder<Public> implements XMLEncryptionBuilder<Public>
{
    @Override
    public Public buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PublicImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Public buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Public", "xenc");
    }
}
