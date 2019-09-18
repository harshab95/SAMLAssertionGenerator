// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface KeyValue extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "KeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValueType", "ds");
    
    DSAKeyValue getDSAKeyValue();
    
    void setDSAKeyValue(final DSAKeyValue p0);
    
    RSAKeyValue getRSAKeyValue();
    
    void setRSAKeyValue(final RSAKeyValue p0);
    
    XMLObject getUnknownXMLObject();
    
    void setUnknownXMLObject(final XMLObject p0);
}
