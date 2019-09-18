// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.Criteria;

public final class KeyAlgorithmCriteria implements Criteria
{
    private String keyAlgorithm;
    
    public KeyAlgorithmCriteria(final String algorithm) {
        this.setKeyAlgorithm(algorithm);
    }
    
    public String getKeyAlgorithm() {
        return this.keyAlgorithm;
    }
    
    public void setKeyAlgorithm(final String algorithm) {
        if (DatatypeHelper.isEmpty(algorithm)) {
            throw new IllegalArgumentException("Key algorithm criteria value must be supplied");
        }
        this.keyAlgorithm = DatatypeHelper.safeTrimOrNullString(algorithm);
    }
}
