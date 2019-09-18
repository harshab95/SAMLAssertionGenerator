// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class EncryptionPropertiesBuilder extends AbstractXMLObjectBuilder<EncryptionProperties> implements XMLEncryptionBuilder<EncryptionProperties>
{
    @Override
    public EncryptionProperties buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new EncryptionPropertiesImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public EncryptionProperties buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
    }
}
