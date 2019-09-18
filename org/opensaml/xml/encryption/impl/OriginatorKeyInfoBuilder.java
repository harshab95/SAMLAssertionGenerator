// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.OriginatorKeyInfo;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class OriginatorKeyInfoBuilder extends AbstractXMLObjectBuilder<OriginatorKeyInfo> implements XMLEncryptionBuilder<OriginatorKeyInfo>
{
    @Override
    public OriginatorKeyInfo buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new OriginatorKeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public OriginatorKeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo", "xenc");
    }
}
