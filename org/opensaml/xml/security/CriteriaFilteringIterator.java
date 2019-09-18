// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Iterator;

public class CriteriaFilteringIterator<T> implements Iterator<T>
{
    private Iterator<? extends T> candidateIter;
    private Set<EvaluableCriteria<T>> criteriaSet;
    private boolean meetAll;
    private boolean unevaledSatisfies;
    private T current;
    
    public CriteriaFilteringIterator(final Iterator<? extends T> candidatesIterator, final Set<EvaluableCriteria<T>> criteria, final boolean meetAllCriteria, final boolean unevaluableSatisfies) {
        this.candidateIter = candidatesIterator;
        this.criteriaSet = criteria;
        this.meetAll = meetAllCriteria;
        this.unevaledSatisfies = unevaluableSatisfies;
        this.current = null;
    }
    
    public boolean hasNext() {
        if (this.current != null) {
            return true;
        }
        this.current = this.getNextMatch();
        return this.current != null;
    }
    
    public T next() {
        if (this.current != null) {
            final T temp = this.current;
            this.current = null;
            return temp;
        }
        final T temp = this.getNextMatch();
        if (temp != null) {
            return temp;
        }
        throw new NoSuchElementException("No more elements are available");
    }
    
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
    }
    
    private T getNextMatch() {
        while (this.candidateIter.hasNext()) {
            final T candidate = (T)this.candidateIter.next();
            if (this.match(candidate)) {
                return candidate;
            }
        }
        return null;
    }
    
    private boolean match(final T candidate) {
        boolean sawOneSatisfied = false;
        if (this.criteriaSet.isEmpty()) {
            return true;
        }
        for (final EvaluableCriteria<T> criteria : this.criteriaSet) {
            final Boolean result = criteria.evaluate(candidate);
            if (result == Boolean.FALSE) {
                if (this.meetAll) {
                    return false;
                }
                continue;
            }
            else if (result == Boolean.TRUE) {
                if (!this.meetAll) {
                    return true;
                }
                sawOneSatisfied = true;
            }
            else {
                if (this.meetAll && !this.unevaledSatisfies) {
                    return false;
                }
                if (!this.meetAll && this.unevaledSatisfies) {
                    return true;
                }
                if (!this.unevaledSatisfies) {
                    continue;
                }
                sawOneSatisfied = true;
            }
        }
        return sawOneSatisfied;
    }
}
