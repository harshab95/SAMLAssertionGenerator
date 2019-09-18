// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.XPath;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XPathBuilder extends AbstractXMLObjectBuilder<XPath> implements XMLSignatureBuilder<XPath>
{
    @Override
    public XPath buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XPathImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public XPath buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "XPath", "ds");
    }
}
