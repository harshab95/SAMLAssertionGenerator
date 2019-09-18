// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface EncryptionMethod extends ValidatingXMLObject, ElementExtensibleXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethodType", "xenc");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";
    
    String getAlgorithm();
    
    void setAlgorithm(final String p0);
    
    KeySize getKeySize();
    
    void setKeySize(final KeySize p0);
    
    OAEPparams getOAEPparams();
    
    void setOAEPparams(final OAEPparams p0);
}
