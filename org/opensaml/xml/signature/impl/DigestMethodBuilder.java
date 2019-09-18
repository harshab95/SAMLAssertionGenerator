// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.DigestMethod;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class DigestMethodBuilder extends AbstractXMLObjectBuilder<DigestMethod> implements XMLSignatureBuilder<DigestMethod>
{
    @Override
    public DigestMethod buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new DigestMethodImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public DigestMethod buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DigestMethod", "xenc");
    }
}
