// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;
import org.opensaml.xml.signature.SignatureTrustEngine;

public class ChainingSignatureTrustEngine implements SignatureTrustEngine
{
    private final Logger log;
    private List<SignatureTrustEngine> engines;
    
    public ChainingSignatureTrustEngine() {
        this.log = LoggerFactory.getLogger((Class)ChainingSignatureTrustEngine.class);
        this.engines = new ArrayList<SignatureTrustEngine>();
    }
    
    public List<SignatureTrustEngine> getChain() {
        return this.engines;
    }
    
    public KeyInfoCredentialResolver getKeyInfoResolver() {
        return null;
    }
    
    public boolean validate(final Signature token, final CriteriaSet trustBasisCriteria) throws SecurityException {
        for (final SignatureTrustEngine engine : this.engines) {
            if (engine.validate(token, trustBasisCriteria)) {
                this.log.debug("Signature was trusted by chain member: {}", (Object)engine.getClass().getName());
                return true;
            }
        }
        return false;
    }
    
    public boolean validate(final byte[] signature, final byte[] content, final String algorithmURI, final CriteriaSet trustBasisCriteria, final Credential candidateCredential) throws SecurityException {
        for (final SignatureTrustEngine engine : this.engines) {
            if (engine.validate(signature, content, algorithmURI, trustBasisCriteria, candidateCredential)) {
                this.log.debug("Signature was trusted by chain member: {}", (Object)engine.getClass().getName());
                return true;
            }
        }
        return false;
    }
}
