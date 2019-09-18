// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.DHKeyValue;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class DHKeyValueBuilder extends AbstractXMLObjectBuilder<DHKeyValue> implements XMLEncryptionBuilder<DHKeyValue>
{
    @Override
    public DHKeyValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new DHKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public DHKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DHKeyValue", "xenc");
    }
}
