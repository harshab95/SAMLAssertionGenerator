// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.PgenCounter;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PgenCounterBuilder extends AbstractXMLObjectBuilder<PgenCounter> implements XMLEncryptionBuilder<PgenCounter>
{
    @Override
    public PgenCounter buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public PgenCounter buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "pgenCounter", "xenc");
    }
}
