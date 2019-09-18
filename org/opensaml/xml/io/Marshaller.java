// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public interface Marshaller
{
    Element marshall(final XMLObject p0) throws MarshallingException;
    
    Element marshall(final XMLObject p0, final Document p1) throws MarshallingException;
    
    Element marshall(final XMLObject p0, final Element p1) throws MarshallingException;
}
