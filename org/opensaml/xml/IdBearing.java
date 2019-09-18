// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;

public interface IdBearing
{
    public static final String XML_ID_ATTR_LOCAL_NAME = "id";
    public static final QName XML_ID_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "id", "xml");
    
    String getXMLId();
    
    void setXMLId(final String p0);
}
