// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Modulus;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class ModulusBuilder extends AbstractXMLObjectBuilder<Modulus> implements XMLSignatureBuilder<Modulus>
{
    @Override
    public Modulus buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new ModulusImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Modulus buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Modulus", "ds");
    }
}
