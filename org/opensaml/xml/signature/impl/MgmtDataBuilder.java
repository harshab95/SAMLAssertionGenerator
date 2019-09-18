// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.MgmtData;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class MgmtDataBuilder extends AbstractXMLObjectBuilder<MgmtData> implements XMLSignatureBuilder<MgmtData>
{
    @Override
    public MgmtData buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new MgmtDataImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public MgmtData buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "MgmtData", "ds");
    }
}
