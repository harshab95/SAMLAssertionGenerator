// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import java.util.Iterator;
import java.util.Set;

public class CriteriaFilteringIterable<T> implements Iterable<T>
{
    private Iterable<? extends T> candidates;
    private Set<EvaluableCriteria<T>> criteriaSet;
    private boolean meetAll;
    private boolean unevaledSatisfies;
    
    public CriteriaFilteringIterable(final Iterable<? extends T> candidatesIterable, final Set<EvaluableCriteria<T>> criteria, final boolean meetAllCriteria, final boolean unevaluableSatisfies) {
        this.candidates = candidatesIterable;
        this.criteriaSet = criteria;
        this.meetAll = meetAllCriteria;
        this.unevaledSatisfies = unevaluableSatisfies;
    }
    
    public Iterator<T> iterator() {
        return new CriteriaFilteringIterator<T>(this.candidates.iterator(), this.criteriaSet, this.meetAll, this.unevaledSatisfies);
    }
}
