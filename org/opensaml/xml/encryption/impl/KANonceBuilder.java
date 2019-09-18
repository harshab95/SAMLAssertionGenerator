// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.KANonce;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KANonceBuilder extends AbstractXMLObjectBuilder<KANonce> implements XMLEncryptionBuilder<KANonce>
{
    @Override
    public KANonce buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KANonceImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KANonce buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KA-Nonce", "xenc");
    }
}
