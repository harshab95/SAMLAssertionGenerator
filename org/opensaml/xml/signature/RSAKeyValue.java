// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface RSAKeyValue extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RSAKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "RSAKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValueType", "ds");
    
    Modulus getModulus();
    
    void setModulus(final Modulus p0);
    
    Exponent getExponent();
    
    void setExponent(final Exponent p0);
}
