// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;

public interface LangBearing
{
    public static final String XML_LANG_ATTR_LOCAL_NAME = "lang";
    public static final QName XML_LANG_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "lang", "xml");
    
    String getXMLLang();
    
    void setXMLLang(final String p0);
}
