// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.util.Iterator;
import org.opensaml.xml.security.credential.criteria.EvaluableCredentialCriteriaRegistry;
import org.opensaml.xml.security.credential.criteria.EvaluableCredentialCriteria;
import org.opensaml.xml.security.Criteria;
import java.util.HashSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.EvaluableCriteria;
import java.util.Set;
import org.opensaml.xml.security.CriteriaFilteringIterable;
import org.opensaml.xml.security.CriteriaSet;

public abstract class AbstractCriteriaFilteringCredentialResolver extends AbstractCredentialResolver
{
    private boolean meetAllCriteria;
    private boolean unevaluableSatisfies;
    
    public AbstractCriteriaFilteringCredentialResolver() {
        this.meetAllCriteria = true;
        this.unevaluableSatisfies = true;
    }
    
    @Override
    public Iterable<Credential> resolve(final CriteriaSet criteriaSet) throws SecurityException {
        final Iterable<Credential> storeCandidates = this.resolveFromSource(criteriaSet);
        final Set<EvaluableCriteria<Credential>> evaluableCriteria = this.getEvaluableCriteria(criteriaSet);
        if (evaluableCriteria.isEmpty()) {
            return storeCandidates;
        }
        return new CriteriaFilteringIterable<Credential>(storeCandidates, evaluableCriteria, this.meetAllCriteria, this.unevaluableSatisfies);
    }
    
    public boolean isMeetAllCriteria() {
        return this.meetAllCriteria;
    }
    
    public void setMeetAllCriteria(final boolean flag) {
        this.meetAllCriteria = flag;
    }
    
    public boolean isUnevaluableSatisfies() {
        return this.unevaluableSatisfies;
    }
    
    public void setUnevaluableSatisfies(final boolean flag) {
        this.unevaluableSatisfies = flag;
    }
    
    protected abstract Iterable<Credential> resolveFromSource(final CriteriaSet p0) throws SecurityException;
    
    private Set<EvaluableCriteria<Credential>> getEvaluableCriteria(final CriteriaSet criteriaSet) throws SecurityException {
        final Set<EvaluableCriteria<Credential>> evaluable = new HashSet<EvaluableCriteria<Credential>>(criteriaSet.size());
        for (final Criteria criteria : criteriaSet) {
            if (criteria instanceof EvaluableCredentialCriteria) {
                evaluable.add((EvaluableCredentialCriteria)criteria);
            }
            else {
                final EvaluableCredentialCriteria evaluableCriteria = EvaluableCredentialCriteriaRegistry.getEvaluator(criteria);
                if (evaluableCriteria == null) {
                    continue;
                }
                evaluable.add(evaluableCriteria);
            }
        }
        return evaluable;
    }
}
