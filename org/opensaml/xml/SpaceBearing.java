// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;

public interface SpaceBearing
{
    public static final String XML_SPACE_ATTR_LOCAL_NAME = "space";
    public static final QName XML_SPACE_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "space", "xml");
    
    XMLSpaceEnum getXMLSpace();
    
    void setXMLSpace(final XMLSpaceEnum p0);
    
    public enum XMLSpaceEnum
    {
        DEFAULT("DEFAULT", 0), 
        PRESERVE("PRESERVE", 1);
        
        private XMLSpaceEnum(final String name, final int ordinal) {
        }
        
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
        
        public static XMLSpaceEnum parseValue(final String value) {
            return valueOf(value.toUpperCase());
        }
    }
}
