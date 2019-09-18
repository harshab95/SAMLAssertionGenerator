// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface X509Data extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509Data";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
    public static final String TYPE_LOCAL_NAME = "X509DataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509DataType", "ds");
    
    List<XMLObject> getXMLObjects();
    
    List<XMLObject> getXMLObjects(final QName p0);
    
    List<X509IssuerSerial> getX509IssuerSerials();
    
    List<X509SKI> getX509SKIs();
    
    List<X509SubjectName> getX509SubjectNames();
    
    List<X509Certificate> getX509Certificates();
    
    List<X509CRL> getX509CRLs();
}
