// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo.provider;

import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.security.credential.CredentialContext;
import java.security.PublicKey;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.util.LazySet;
import org.opensaml.xml.security.credential.BasicCredential;
import java.security.KeyException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.security.criteria.KeyAlgorithmCriteria;
import org.opensaml.xml.security.credential.Credential;
import java.util.Collection;
import org.opensaml.xml.security.keyinfo.KeyInfoResolutionContext;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.XMLObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class RSAKeyValueProvider extends AbstractKeyInfoProvider
{
    private final Logger log;
    
    public RSAKeyValueProvider() {
        this.log = LoggerFactory.getLogger((Class)RSAKeyValueProvider.class);
    }
    
    public boolean handles(final XMLObject keyInfoChild) {
        return this.getRSAKeyValue(keyInfoChild) != null;
    }
    
    public Collection<Credential> process(final KeyInfoCredentialResolver resolver, final XMLObject keyInfoChild, final CriteriaSet criteriaSet, final KeyInfoResolutionContext kiContext) throws SecurityException {
        final RSAKeyValue keyValue = this.getRSAKeyValue(keyInfoChild);
        if (keyValue == null) {
            return null;
        }
        final KeyAlgorithmCriteria algorithmCriteria = criteriaSet.get(KeyAlgorithmCriteria.class);
        if (algorithmCriteria != null && algorithmCriteria.getKeyAlgorithm() != null && !algorithmCriteria.getKeyAlgorithm().equals("RSA")) {
            this.log.debug("Criteria specified non-RSA key algorithm, skipping");
            return null;
        }
        this.log.debug("Attempting to extract credential from an RSAKeyValue");
        PublicKey pubKey = null;
        try {
            pubKey = KeyInfoHelper.getRSAKey(keyValue);
        }
        catch (KeyException e) {
            this.log.error("Error extracting RSA key value", (Throwable)e);
            throw new SecurityException("Error extracting RSA key value", e);
        }
        final BasicCredential cred = new BasicCredential();
        cred.setPublicKey(pubKey);
        if (kiContext != null) {
            cred.getKeyNames().addAll(kiContext.getKeyNames());
        }
        final CredentialContext credContext = this.buildCredentialContext(kiContext);
        if (credContext != null) {
            cred.getCredentalContextSet().add(credContext);
        }
        this.log.debug("Credential successfully extracted from RSAKeyValue");
        final LazySet<Credential> credentialSet = new LazySet<Credential>();
        credentialSet.add(cred);
        return credentialSet;
    }
    
    protected RSAKeyValue getRSAKeyValue(final XMLObject xmlObject) {
        if (xmlObject == null) {
            return null;
        }
        if (xmlObject instanceof RSAKeyValue) {
            return (RSAKeyValue)xmlObject;
        }
        if (xmlObject instanceof KeyValue) {
            return ((KeyValue)xmlObject).getRSAKeyValue();
        }
        return null;
    }
}
