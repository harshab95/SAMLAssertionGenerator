// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class CipherDataBuilder extends AbstractXMLObjectBuilder<CipherData> implements XMLEncryptionBuilder<CipherData>
{
    @Override
    public CipherData buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new CipherDataImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public CipherData buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
    }
}
