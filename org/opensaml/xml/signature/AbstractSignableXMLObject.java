// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.opensaml.xml.AbstractXMLObject;

public abstract class AbstractSignableXMLObject extends AbstractXMLObject implements SignableXMLObject
{
    private Signature signature;
    
    protected AbstractSignableXMLObject(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public boolean isSigned() {
        final Element domElement = this.getDOM();
        if (domElement == null) {
            return false;
        }
        final NodeList children = domElement.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            if (children.item(i).getNodeType() == 1) {
                final Element childElement = (Element)children.item(i);
                if (childElement.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && childElement.getLocalName().equals("Signature")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Signature getSignature() {
        return this.signature;
    }
    
    public void setSignature(final Signature newSignature) {
        this.signature = this.prepareForAssignment(this.signature, newSignature);
    }
}
