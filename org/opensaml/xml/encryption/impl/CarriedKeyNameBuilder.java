// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.CarriedKeyName;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class CarriedKeyNameBuilder extends AbstractXMLObjectBuilder<CarriedKeyName> implements XMLEncryptionBuilder<CarriedKeyName>
{
    @Override
    public CarriedKeyName buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new CarriedKeyNameImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public CarriedKeyName buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName", "xenc");
    }
}
