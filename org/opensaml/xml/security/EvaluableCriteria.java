// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

public interface EvaluableCriteria<T> extends Criteria
{
    Boolean evaluate(final T p0);
}
