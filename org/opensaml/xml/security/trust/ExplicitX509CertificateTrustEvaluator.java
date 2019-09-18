// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.X509Credential;
import java.util.Iterator;
import java.security.cert.X509Certificate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ExplicitX509CertificateTrustEvaluator
{
    private final Logger log;
    
    public ExplicitX509CertificateTrustEvaluator() {
        this.log = LoggerFactory.getLogger((Class)ExplicitX509CertificateTrustEvaluator.class);
    }
    
    public boolean validate(final X509Certificate untrustedCertificate, final X509Certificate trustedCertificate) {
        return untrustedCertificate.equals(trustedCertificate);
    }
    
    public boolean validate(final X509Certificate untrustedCertificate, final Iterable<X509Certificate> trustedCertificates) {
        for (final X509Certificate trustedCertificate : trustedCertificates) {
            if (untrustedCertificate.equals(trustedCertificate)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validate(final X509Credential untrustedCredential, final X509Credential trustedCredential) {
        final X509Certificate untrustedCertificate = untrustedCredential.getEntityCertificate();
        final X509Certificate trustedCertificate = trustedCredential.getEntityCertificate();
        if (untrustedCertificate == null) {
            this.log.debug("Untrusted credential contained no entity certificate, unable to evaluate");
            return false;
        }
        if (trustedCertificate == null) {
            this.log.debug("Trusted credential contained no entity certificate, unable to evaluate");
            return false;
        }
        if (this.validate(untrustedCertificate, trustedCertificate)) {
            this.log.debug("Successfully validated untrusted credential against trusted certificate");
            return true;
        }
        this.log.debug("Failed to validate untrusted credential against trusted certificate");
        return false;
    }
    
    public boolean validate(final X509Credential untrustedCredential, final Iterable<Credential> trustedCredentials) {
        for (final Credential trustedCredential : trustedCredentials) {
            if (!(trustedCredential instanceof X509Credential)) {
                this.log.debug("Skipping evaluation against trusted, non-X509Credential");
            }
            else {
                final X509Credential trustedX509Credential = (X509Credential)trustedCredential;
                if (this.validate(untrustedCredential, trustedX509Credential)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
