// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

public enum UsageType
{
    ENCRYPTION("ENCRYPTION", 0), 
    SIGNING("SIGNING", 1), 
    UNSPECIFIED("UNSPECIFIED", 2);
    
    private UsageType(final String name, final int ordinal) {
    }
}
