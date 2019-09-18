// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface X509IssuerSerial extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509IssuerSerial";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial", "ds");
    public static final String TYPE_LOCAL_NAME = "X509IssuerSerialType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerialType", "ds");
    
    X509IssuerName getX509IssuerName();
    
    void setX509IssuerName(final X509IssuerName p0);
    
    X509SerialNumber getX509SerialNumber();
    
    void setX509SerialNumber(final X509SerialNumber p0);
}
