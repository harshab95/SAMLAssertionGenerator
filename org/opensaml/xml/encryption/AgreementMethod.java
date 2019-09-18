// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface AgreementMethod extends ValidatingXMLObject, ElementExtensibleXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AgreementMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
    public static final String TYPE_LOCAL_NAME = "AgreementMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethodType", "xenc");
    public static final String ALGORITHM_ATTRIBUTE_NAME = "Algorithm";
    
    String getAlgorithm();
    
    void setAlgorithm(final String p0);
    
    KANonce getKANonce();
    
    void setKANonce(final KANonce p0);
    
    OriginatorKeyInfo getOriginatorKeyInfo();
    
    void setOriginatorKeyInfo(final OriginatorKeyInfo p0);
    
    RecipientKeyInfo getRecipientKeyInfo();
    
    void setRecipientKeyInfo(final RecipientKeyInfo p0);
}
