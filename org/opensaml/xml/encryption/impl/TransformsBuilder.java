// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.Transforms;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class TransformsBuilder extends AbstractXMLObjectBuilder<Transforms> implements XMLEncryptionBuilder<Transforms>
{
    @Override
    public Transforms buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new TransformsImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Transforms buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
    }
}
