// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;
import org.opensaml.xml.AttributeExtensibleXMLObject;
import org.opensaml.xml.ElementExtensibleXMLObject;

public interface XSAny extends ElementExtensibleXMLObject, AttributeExtensibleXMLObject, ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "anyType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "anyType", "xs");
    
    String getTextContent();
    
    void setTextContent(final String p0);
}
