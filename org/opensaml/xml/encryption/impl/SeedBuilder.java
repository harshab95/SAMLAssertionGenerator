// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.Seed;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class SeedBuilder extends AbstractXMLObjectBuilder<Seed> implements XMLEncryptionBuilder<Seed>
{
    @Override
    public Seed buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new SeedImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Seed buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "seed", "xenc");
    }
}
