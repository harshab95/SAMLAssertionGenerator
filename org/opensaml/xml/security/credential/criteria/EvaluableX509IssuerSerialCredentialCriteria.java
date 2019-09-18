// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.cert.X509Certificate;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.x509.X509IssuerSerialCriteria;
import java.math.BigInteger;
import javax.security.auth.x500.X500Principal;
import org.slf4j.Logger;

public class EvaluableX509IssuerSerialCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private X500Principal issuer;
    private BigInteger serialNumber;
    
    public EvaluableX509IssuerSerialCredentialCriteria(final X509IssuerSerialCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509IssuerSerialCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.issuer = criteria.getIssuerName();
        this.serialNumber = criteria.getSerialNumber();
    }
    
    public EvaluableX509IssuerSerialCredentialCriteria(final X500Principal newIssuer, final BigInteger newSerialNumber) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509IssuerSerialCredentialCriteria.class);
        if (newIssuer == null || newSerialNumber == null) {
            throw new IllegalArgumentException("Issuer and serial number may not be null");
        }
        this.issuer = newIssuer;
        this.serialNumber = newSerialNumber;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, does not satisfy issuer name and serial number criteria");
            return Boolean.FALSE;
        }
        final X509Credential x509Cred = (X509Credential)target;
        final X509Certificate entityCert = x509Cred.getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return Boolean.FALSE;
        }
        if (!entityCert.getIssuerX500Principal().equals(this.issuer)) {
            return false;
        }
        final Boolean result = entityCert.getSerialNumber().equals(this.serialNumber);
        return result;
    }
}
