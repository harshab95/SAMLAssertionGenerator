// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import java.util.List;
import org.opensaml.xml.security.credential.Credential;
import javax.xml.namespace.QName;
import org.opensaml.xml.XMLObject;

public interface Signature extends XMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Signature";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
    public static final String TYPE_LOCAL_NAME = "SignatureType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureType", "ds");
    
    String getCanonicalizationAlgorithm();
    
    void setCanonicalizationAlgorithm(final String p0);
    
    String getSignatureAlgorithm();
    
    void setSignatureAlgorithm(final String p0);
    
    Integer getHMACOutputLength();
    
    void setHMACOutputLength(final Integer p0);
    
    Credential getSigningCredential();
    
    void setSigningCredential(final Credential p0);
    
    KeyInfo getKeyInfo();
    
    void setKeyInfo(final KeyInfo p0);
    
    List<ContentReference> getContentReferences();
}
