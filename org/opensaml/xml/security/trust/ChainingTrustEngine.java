// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import org.opensaml.xml.security.CriteriaSet;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;

public class ChainingTrustEngine<TokenType> implements TrustEngine<TokenType>
{
    private final Logger log;
    private List<TrustEngine<TokenType>> engines;
    
    public ChainingTrustEngine() {
        this.log = LoggerFactory.getLogger((Class)ChainingTrustEngine.class);
        this.engines = new ArrayList<TrustEngine<TokenType>>();
    }
    
    public List<TrustEngine<TokenType>> getChain() {
        return this.engines;
    }
    
    public boolean validate(final TokenType token, final CriteriaSet trustBasisCriteria) throws SecurityException {
        for (final TrustEngine<TokenType> engine : this.engines) {
            if (engine.validate(token, trustBasisCriteria)) {
                this.log.debug("Token was trusted by chain member: {}", (Object)engine.getClass().getName());
                return true;
            }
        }
        return false;
    }
}
