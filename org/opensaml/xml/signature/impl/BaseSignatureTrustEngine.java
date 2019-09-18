// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.Criteria;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.keyinfo.KeyInfoCriteria;
import org.opensaml.xml.signature.Signature;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.opensaml.xml.signature.SignatureTrustEngine;

public abstract class BaseSignatureTrustEngine<TrustBasisType> implements SignatureTrustEngine
{
    private final Logger log;
    private KeyInfoCredentialResolver keyInfoCredentialResolver;
    
    public BaseSignatureTrustEngine(final KeyInfoCredentialResolver keyInfoResolver) {
        this.log = LoggerFactory.getLogger((Class)BaseSignatureTrustEngine.class);
        if (keyInfoResolver == null) {
            throw new IllegalArgumentException("KeyInfo credential resolver may not be null");
        }
        this.keyInfoCredentialResolver = keyInfoResolver;
    }
    
    public KeyInfoCredentialResolver getKeyInfoResolver() {
        return this.keyInfoCredentialResolver;
    }
    
    protected boolean validate(final Signature signature, final TrustBasisType trustBasis) throws SecurityException {
        this.log.debug("Attempting to verify signature and establish trust using KeyInfo-derived credentials");
        if (signature.getKeyInfo() != null) {
            final KeyInfoCriteria keyInfoCriteria = new KeyInfoCriteria(signature.getKeyInfo());
            final CriteriaSet keyInfoCriteriaSet = new CriteriaSet(keyInfoCriteria);
            for (final Credential kiCred : this.getKeyInfoResolver().resolve(keyInfoCriteriaSet)) {
                if (this.verifySignature(signature, kiCred)) {
                    this.log.debug("Successfully verified signature using KeyInfo-derived credential");
                    this.log.debug("Attempting to establish trust of KeyInfo-derived credential");
                    if (this.evaluateTrust(kiCred, trustBasis)) {
                        this.log.debug("Successfully established trust of KeyInfo-derived credential");
                        return true;
                    }
                    this.log.debug("Failed to establish trust of KeyInfo-derived credential");
                }
            }
        }
        else {
            this.log.debug("Signature contained no KeyInfo element, could not resolve verification credentials");
        }
        this.log.debug("Failed to verify signature and/or establish trust using any KeyInfo-derived credentials");
        return false;
    }
    
    protected abstract boolean evaluateTrust(final Credential p0, final TrustBasisType p1) throws SecurityException;
    
    protected boolean verifySignature(final Signature signature, final Credential credential) {
        final SignatureValidator validator = new SignatureValidator(credential);
        try {
            validator.validate(signature);
        }
        catch (ValidationException e) {
            this.log.debug("Signature validation using candidate validation credential failed", (Throwable)e);
            return false;
        }
        this.log.debug("Signature validation using candidate credential was successful");
        return true;
    }
    
    protected void checkParams(final Signature signature, final CriteriaSet trustBasisCriteria) throws SecurityException {
        if (signature == null) {
            throw new SecurityException("Signature was null");
        }
        if (trustBasisCriteria == null) {
            throw new SecurityException("Trust basis criteria set was null");
        }
        if (trustBasisCriteria.isEmpty()) {
            throw new SecurityException("Trust basis criteria set was empty");
        }
    }
    
    protected void checkParamsRaw(final byte[] signature, final byte[] content, final String algorithmURI, final CriteriaSet trustBasisCriteria) throws SecurityException {
        if (signature == null || signature.length == 0) {
            throw new SecurityException("Signature byte array was null or empty");
        }
        if (content == null || content.length == 0) {
            throw new SecurityException("Content byte array was null or empty");
        }
        if (DatatypeHelper.isEmpty(algorithmURI)) {
            throw new SecurityException("Signature algorithm was null or empty");
        }
        if (trustBasisCriteria == null) {
            throw new SecurityException("Trust basis criteria set was null");
        }
        if (trustBasisCriteria.isEmpty()) {
            throw new SecurityException("Trust basis criteria set was empty");
        }
    }
}
