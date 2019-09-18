// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class DSAKeyValueBuilder extends AbstractXMLObjectBuilder<DSAKeyValue> implements XMLSignatureBuilder<DSAKeyValue>
{
    @Override
    public DSAKeyValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new DSAKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public DSAKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue", "ds");
    }
}
