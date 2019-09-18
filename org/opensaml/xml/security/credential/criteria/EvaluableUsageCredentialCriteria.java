// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.credential.UsageType;
import org.slf4j.Logger;

public class EvaluableUsageCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private UsageType usage;
    
    public EvaluableUsageCredentialCriteria(final UsageCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableUsageCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.usage = criteria.getUsage();
    }
    
    public EvaluableUsageCredentialCriteria(final UsageType newUsage) {
        this.log = LoggerFactory.getLogger((Class)EvaluableUsageCredentialCriteria.class);
        if (newUsage == null) {
            throw new IllegalArgumentException("Usage may not be null");
        }
        this.usage = newUsage;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        final UsageType credUsage = target.getUsageType();
        if (credUsage == null) {
            this.log.info("Could not evaluate criteria, credential contained no usage specifier");
            return null;
        }
        final Boolean result = this.matchUsage(credUsage, this.usage);
        return result;
    }
    
    protected boolean matchUsage(final UsageType credentialUsage, final UsageType criteriaUsage) {
        return credentialUsage == UsageType.UNSPECIFIED || criteriaUsage == UsageType.UNSPECIFIED || credentialUsage == criteriaUsage;
    }
}
