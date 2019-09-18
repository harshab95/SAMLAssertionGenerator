// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;

public interface PKIXValidationInformation
{
    Integer getVerificationDepth();
    
    Collection<X509Certificate> getCertificates();
    
    Collection<X509CRL> getCRLs();
}
