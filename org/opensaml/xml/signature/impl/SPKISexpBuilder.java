// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.SPKISexp;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class SPKISexpBuilder extends AbstractXMLObjectBuilder<SPKISexp> implements XMLSignatureBuilder<SPKISexp>
{
    @Override
    public SPKISexp buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new SPKISexpImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public SPKISexp buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKISexp", "ds");
    }
}
