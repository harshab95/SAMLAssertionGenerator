// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.PgenCounter;
import org.opensaml.xml.encryption.Seed;
import org.opensaml.xml.encryption.Public;
import org.opensaml.xml.encryption.Generator;
import org.opensaml.xml.encryption.Q;
import org.opensaml.xml.encryption.P;
import org.opensaml.xml.encryption.DHKeyValue;
import org.opensaml.xml.XMLObject;

public class DHKeyValueUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final DHKeyValue keyValue = (DHKeyValue)parentXMLObject;
        if (childXMLObject instanceof P) {
            keyValue.setP((P)childXMLObject);
        }
        else if (childXMLObject instanceof Q) {
            keyValue.setQ((Q)childXMLObject);
        }
        else if (childXMLObject instanceof Generator) {
            keyValue.setGenerator((Generator)childXMLObject);
        }
        else if (childXMLObject instanceof Public) {
            keyValue.setPublic((Public)childXMLObject);
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
