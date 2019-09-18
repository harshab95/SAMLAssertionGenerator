// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.w3c.dom.Element;
import javax.xml.namespace.QName;

public interface XMLObjectBuilder<XMLObjectType extends XMLObject>
{
    XMLObjectType buildObject(final QName p0);
    
    XMLObjectType buildObject(final QName p0, final QName p1);
    
    XMLObjectType buildObject(final String p0, final String p1, final String p2);
    
    XMLObjectType buildObject(final String p0, final String p1, final String p2, final QName p3);
    
    XMLObjectType buildObject(final Element p0);
}
