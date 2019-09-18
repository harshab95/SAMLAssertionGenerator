// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.AgreementMethod;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class AgreementMethodBuilder extends AbstractXMLObjectBuilder<AgreementMethod> implements XMLEncryptionBuilder<AgreementMethod>
{
    @Override
    public AgreementMethod buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new AgreementMethodImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public AgreementMethod buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
    }
}
