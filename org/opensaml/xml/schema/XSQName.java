// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface XSQName extends ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "QName";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "QName", "xs");
    
    QName getValue();
    
    void setValue(final QName p0);
}
