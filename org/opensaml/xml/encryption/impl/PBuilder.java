// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.P;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PBuilder extends AbstractXMLObjectBuilder<P> implements XMLEncryptionBuilder<P>
{
    @Override
    public P buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public P buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "P", "xenc");
    }
}
