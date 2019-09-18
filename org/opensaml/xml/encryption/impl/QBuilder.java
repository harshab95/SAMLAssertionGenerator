// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.Q;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class QBuilder extends AbstractXMLObjectBuilder<Q> implements XMLEncryptionBuilder<Q>
{
    @Override
    public Q buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new QImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Q buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Q", "xenc");
    }
}
