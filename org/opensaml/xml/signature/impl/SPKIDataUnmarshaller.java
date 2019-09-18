// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.XMLObject;

public class SPKIDataUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final SPKIData spkiData = (SPKIData)parentXMLObject;
        spkiData.getXMLObjects().add(childXMLObject);
    }
}
