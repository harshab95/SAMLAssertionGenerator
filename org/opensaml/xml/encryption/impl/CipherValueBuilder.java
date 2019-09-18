// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.CipherValue;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class CipherValueBuilder extends AbstractXMLObjectBuilder<CipherValue> implements XMLEncryptionBuilder<CipherValue>
{
    @Override
    public CipherValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new CipherValueImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public CipherValue buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherValue", "xenc");
    }
}
