// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;

public interface BaseBearing
{
    public static final String XML_BASE_ATTR_LOCAL_NAME = "base";
    public static final QName XML_BASE_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "base", "xml");
    
    String getXMLBase();
    
    void setXMLBase(final String p0);
}
