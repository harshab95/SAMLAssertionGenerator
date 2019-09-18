// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PGPDataBuilder extends AbstractXMLObjectBuilder<PGPData> implements XMLSignatureBuilder<PGPData>
{
    @Override
    public PGPData buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PGPDataImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public PGPData buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
    }
}
