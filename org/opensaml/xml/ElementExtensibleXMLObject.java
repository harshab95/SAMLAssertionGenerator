// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;
import java.util.List;

public interface ElementExtensibleXMLObject extends XMLObject
{
    List<XMLObject> getUnknownXMLObjects();
    
    List<XMLObject> getUnknownXMLObjects(final QName p0);
}
