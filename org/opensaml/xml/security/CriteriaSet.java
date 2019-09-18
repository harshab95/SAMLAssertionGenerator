// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import org.opensaml.xml.util.ClassIndexedSet;

public class CriteriaSet extends ClassIndexedSet<Criteria>
{
    public CriteriaSet() {
    }
    
    public CriteriaSet(final Criteria criteria) {
        this.add(criteria);
    }
}
