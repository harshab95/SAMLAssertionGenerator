// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import java.util.HashSet;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class StaticPKIXValidationInformationResolver implements PKIXValidationInformationResolver
{
    private List<PKIXValidationInformation> pkixInfo;
    private Set<String> trustedNames;
    
    public StaticPKIXValidationInformationResolver(final List<PKIXValidationInformation> info, final Set<String> names) {
        if (info != null) {
            this.pkixInfo = new ArrayList<PKIXValidationInformation>(info);
        }
        else {
            this.pkixInfo = (List<PKIXValidationInformation>)Collections.EMPTY_LIST;
        }
        if (names != null) {
            this.trustedNames = new HashSet<String>(names);
        }
        else {
            this.trustedNames = (Set<String>)Collections.EMPTY_SET;
        }
    }
    
    public Set<String> resolveTrustedNames(final CriteriaSet criteriaSet) throws SecurityException, UnsupportedOperationException {
        return this.trustedNames;
    }
    
    public boolean supportsTrustedNameResolution() {
        return true;
    }
    
    public Iterable<PKIXValidationInformation> resolve(final CriteriaSet criteria) throws SecurityException {
        return this.pkixInfo;
    }
    
    public PKIXValidationInformation resolveSingle(final CriteriaSet criteria) throws SecurityException {
        if (!this.pkixInfo.isEmpty()) {
            return this.pkixInfo.get(0);
        }
        return null;
    }
}
