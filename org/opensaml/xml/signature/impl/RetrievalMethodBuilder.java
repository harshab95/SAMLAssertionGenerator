// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class RetrievalMethodBuilder extends AbstractXMLObjectBuilder<RetrievalMethod> implements XMLSignatureBuilder<RetrievalMethod>
{
    @Override
    public RetrievalMethod buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new RetrievalMethodImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public RetrievalMethod buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod", "ds");
    }
}
