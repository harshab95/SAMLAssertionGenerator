// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import javax.security.auth.x500.X500Principal;
import org.opensaml.xml.security.Criteria;

public final class X509SubjectNameCriteria implements Criteria
{
    private X500Principal subjectName;
    
    public X509SubjectNameCriteria(final X500Principal subject) {
        this.setSubjectName(subject);
    }
    
    public X500Principal getSubjectName() {
        return this.subjectName;
    }
    
    public void setSubjectName(final X500Principal subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject principal criteria value must be supplied");
        }
        this.subjectName = subject;
    }
}
