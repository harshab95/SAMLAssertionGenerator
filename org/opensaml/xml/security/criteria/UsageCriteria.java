// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.Criteria;

public final class UsageCriteria implements Criteria
{
    private UsageType credUsage;
    
    public UsageCriteria(final UsageType usage) {
        this.setUsage(usage);
    }
    
    public UsageType getUsage() {
        return this.credUsage;
    }
    
    public void setUsage(final UsageType usage) {
        if (usage != null) {
            this.credUsage = usage;
        }
        else {
            this.credUsage = UsageType.UNSPECIFIED;
        }
    }
}
