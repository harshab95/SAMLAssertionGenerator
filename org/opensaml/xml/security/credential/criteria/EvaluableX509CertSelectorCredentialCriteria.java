// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import java.security.cert.X509CertSelector;
import org.slf4j.Logger;

public class EvaluableX509CertSelectorCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private X509CertSelector certSelector;
    
    public EvaluableX509CertSelectorCredentialCriteria(final X509CertSelector newSelector) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509CertSelectorCredentialCriteria.class);
        if (newSelector == null) {
            throw new IllegalArgumentException("X509 cert selector may not be null");
        }
        this.certSelector = newSelector;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, can not evaluate X509CertSelector criteria");
            return Boolean.FALSE;
        }
        final X509Credential x509Cred = (X509Credential)target;
        final X509Certificate entityCert = x509Cred.getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, can not evaluate X509CertSelector criteria");
            return Boolean.FALSE;
        }
        final Boolean result = this.certSelector.match(entityCert);
        return result;
    }
}
