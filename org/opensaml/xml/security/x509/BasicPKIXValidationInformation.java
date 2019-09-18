// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class BasicPKIXValidationInformation implements PKIXValidationInformation
{
    private Collection<X509Certificate> trustAnchors;
    private Collection<X509CRL> trustedCRLs;
    private Integer verificationDepth;
    
    public BasicPKIXValidationInformation(final Collection<X509Certificate> anchors, final Collection<X509CRL> crls, final Integer depth) {
        this.trustAnchors = anchors;
        this.trustedCRLs = crls;
        this.verificationDepth = depth;
    }
    
    public Collection<X509CRL> getCRLs() {
        return this.trustedCRLs;
    }
    
    public Collection<X509Certificate> getCertificates() {
        return this.trustAnchors;
    }
    
    public Integer getVerificationDepth() {
        return this.verificationDepth;
    }
}
