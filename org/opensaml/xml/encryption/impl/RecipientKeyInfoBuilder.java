// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.RecipientKeyInfo;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class RecipientKeyInfoBuilder extends AbstractXMLObjectBuilder<RecipientKeyInfo> implements XMLEncryptionBuilder<RecipientKeyInfo>
{
    @Override
    public RecipientKeyInfo buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new RecipientKeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public RecipientKeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo", "xenc");
    }
}
