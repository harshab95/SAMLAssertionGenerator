// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface DSAKeyValue extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DSAKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "DSAKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValueType", "ds");
    
    P getP();
    
    void setP(final P p0);
    
    Q getQ();
    
    void setQ(final Q p0);
    
    G getG();
    
    void setG(final G p0);
    
    Y getY();
    
    void setY(final Y p0);
    
    J getJ();
    
    void setJ(final J p0);
    
    Seed getSeed();
    
    void setSeed(final Seed p0);
    
    PgenCounter getPgenCounter();
    
    void setPgenCounter(final PgenCounter p0);
}
