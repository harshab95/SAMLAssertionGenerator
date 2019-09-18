// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.opensaml.xml.security.x509.X509Util;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.x509.X509SubjectKeyIdentifierCriteria;
import org.slf4j.Logger;

public class EvaluableX509SubjectKeyIdentifierCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private byte[] ski;
    
    public EvaluableX509SubjectKeyIdentifierCredentialCriteria(final X509SubjectKeyIdentifierCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509SubjectKeyIdentifierCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.ski = criteria.getSubjectKeyIdentifier();
    }
    
    public EvaluableX509SubjectKeyIdentifierCredentialCriteria(final byte[] newSKI) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509SubjectKeyIdentifierCredentialCriteria.class);
        if (newSKI == null || newSKI.length == 0) {
            throw new IllegalArgumentException("Subject key identifier may not be null or empty");
        }
        this.ski = newSKI;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, does not satisfy subject key identifier criteria");
            return Boolean.FALSE;
        }
        final X509Credential x509Cred = (X509Credential)target;
        final X509Certificate entityCert = x509Cred.getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return Boolean.FALSE;
        }
        final byte[] credSKI = X509Util.getSubjectKeyIdentifier(entityCert);
        if (credSKI == null || credSKI.length == 0) {
            this.log.info("Could not evaluate criteria, certificate contained no subject key identifier extension");
            return null;
        }
        final Boolean result = Arrays.equals(this.ski, credSKI);
        return result;
    }
}
