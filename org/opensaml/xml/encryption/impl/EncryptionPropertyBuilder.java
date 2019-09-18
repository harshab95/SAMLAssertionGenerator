// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.EncryptionProperty;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class EncryptionPropertyBuilder extends AbstractXMLObjectBuilder<EncryptionProperty> implements XMLEncryptionBuilder<EncryptionProperty>
{
    @Override
    public EncryptionProperty buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new EncryptionPropertyImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public EncryptionProperty buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
    }
}
