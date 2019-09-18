// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.opensaml.xml.XMLObject;
import org.w3c.dom.Element;

public interface Unmarshaller
{
    XMLObject unmarshall(final Element p0) throws UnmarshallingException;
}
