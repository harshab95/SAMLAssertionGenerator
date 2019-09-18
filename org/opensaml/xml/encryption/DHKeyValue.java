// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface DHKeyValue extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DHKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValue", "xenc");
    public static final String TYPE_LOCAL_NAME = "DHKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValueType", "xenc");
    
    P getP();
    
    void setP(final P p0);
    
    Q getQ();
    
    void setQ(final Q p0);
    
    Generator getGenerator();
    
    void setGenerator(final Generator p0);
    
    Public getPublic();
    
    void setPublic(final Public p0);
    
    Seed getSeed();
    
    void setSeed(final Seed p0);
    
    PgenCounter getPgenCounter();
    
    void setPgenCounter(final PgenCounter p0);
}
