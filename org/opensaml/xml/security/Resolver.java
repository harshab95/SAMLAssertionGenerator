// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

public interface Resolver<ProductType, CriteriaType>
{
    Iterable<ProductType> resolve(final CriteriaType p0) throws SecurityException;
    
    ProductType resolveSingle(final CriteriaType p0) throws SecurityException;
}
