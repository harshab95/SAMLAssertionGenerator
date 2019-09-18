// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import java.math.BigInteger;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface X509SerialNumber extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509SerialNumber";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
    public static final String TYPE_LOCAL_NAME = "integer";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xs");
    
    BigInteger getValue();
    
    void setValue(final BigInteger p0);
}
