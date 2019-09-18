// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.cert.X509Certificate;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.x509.X509SubjectNameCriteria;
import javax.security.auth.x500.X500Principal;
import org.slf4j.Logger;

public class EvaluableX509SubjectNameCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private X500Principal subjectName;
    
    public EvaluableX509SubjectNameCredentialCriteria(final X509SubjectNameCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509SubjectNameCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.subjectName = criteria.getSubjectName();
    }
    
    public EvaluableX509SubjectNameCredentialCriteria(final X500Principal newSubjectName) {
        this.log = LoggerFactory.getLogger((Class)EvaluableX509SubjectNameCredentialCriteria.class);
        if (newSubjectName == null) {
            throw new IllegalArgumentException("Subject name may not be null");
        }
        this.subjectName = newSubjectName;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, does not satisfy subject name criteria");
            return Boolean.FALSE;
        }
        final X509Credential x509Cred = (X509Credential)target;
        final X509Certificate entityCert = x509Cred.getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return Boolean.FALSE;
        }
        final Boolean result = entityCert.getSubjectX500Principal().equals(this.subjectName);
        return result;
    }
}
