// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class EncryptionMethodBuilder extends AbstractXMLObjectBuilder<EncryptionMethod> implements XMLEncryptionBuilder<EncryptionMethod>
{
    @Override
    public EncryptionMethod buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new EncryptionMethodImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public EncryptionMethod buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
    }
}
