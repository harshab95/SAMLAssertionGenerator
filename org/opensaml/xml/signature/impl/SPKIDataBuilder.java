// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class SPKIDataBuilder extends AbstractXMLObjectBuilder<SPKIData> implements XMLSignatureBuilder<SPKIData>
{
    @Override
    public SPKIData buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new SPKIDataImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public SPKIData buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
    }
}
