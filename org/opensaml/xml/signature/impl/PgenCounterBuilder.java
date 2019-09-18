// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.PgenCounter;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PgenCounterBuilder extends AbstractXMLObjectBuilder<PgenCounter> implements XMLSignatureBuilder<PgenCounter>
{
    @Override
    public PgenCounter buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public PgenCounter buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PgenCounter", "ds");
    }
}
