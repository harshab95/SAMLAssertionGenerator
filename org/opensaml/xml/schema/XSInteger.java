// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface XSInteger extends ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "integer";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xs");
    
    Integer getValue();
    
    void setValue(final Integer p0);
}
