// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.PgenCounter;
import org.opensaml.xml.signature.Seed;
import org.opensaml.xml.signature.J;
import org.opensaml.xml.signature.Y;
import org.opensaml.xml.signature.G;
import org.opensaml.xml.signature.Q;
import org.opensaml.xml.signature.P;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.XMLObject;

public class DSAKeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final DSAKeyValue keyValue = (DSAKeyValue)parentXMLObject;
        if (childXMLObject instanceof P) {
            keyValue.setP((P)childXMLObject);
        }
        else if (childXMLObject instanceof Q) {
            keyValue.setQ((Q)childXMLObject);
        }
        else if (childXMLObject instanceof G) {
            keyValue.setG((G)childXMLObject);
        }
        else if (childXMLObject instanceof Y) {
            keyValue.setY((Y)childXMLObject);
        }
        else if (childXMLObject instanceof J) {
            keyValue.setJ((J)childXMLObject);
        }
        else if (childXMLObject instanceof Seed) {
            keyValue.setSeed((Seed)childXMLObject);
        }
        else if (childXMLObject instanceof PgenCounter) {
            keyValue.setPgenCounter((PgenCounter)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
