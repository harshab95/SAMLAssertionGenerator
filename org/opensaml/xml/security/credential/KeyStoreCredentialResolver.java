// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.security.cert.X509Certificate;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509Credential;
import java.security.GeneralSecurityException;
import java.security.UnrecoverableEntryException;
import org.opensaml.xml.security.SecurityException;
import java.util.Collections;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.CriteriaSet;
import java.security.KeyStoreException;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.security.KeyStore;
import org.slf4j.Logger;

public class KeyStoreCredentialResolver extends AbstractCriteriaFilteringCredentialResolver
{
    private final Logger log;
    private KeyStore keyStore;
    private Map<String, String> keyPasswords;
    private UsageType keystoreUsage;
    
    public KeyStoreCredentialResolver(final KeyStore store, final Map<String, String> passwords) throws IllegalArgumentException {
        this(store, passwords, null);
    }
    
    public KeyStoreCredentialResolver(final KeyStore store, final Map<String, String> passwords, final UsageType usage) throws IllegalArgumentException {
        this.log = LoggerFactory.getLogger((Class)KeyStoreCredentialResolver.class);
        if (store == null) {
            throw new IllegalArgumentException("Provided key store may not be null.");
        }
        try {
            store.size();
        }
        catch (KeyStoreException e) {
            throw new IllegalArgumentException("Keystore has not been initialized.");
        }
        this.keyStore = store;
        if (usage != null) {
            this.keystoreUsage = usage;
        }
        else {
            this.keystoreUsage = UsageType.UNSPECIFIED;
        }
        this.keyPasswords = passwords;
    }
    
    @Override
    protected Iterable<Credential> resolveFromSource(final CriteriaSet criteriaSet) throws SecurityException {
        this.checkCriteriaRequirements(criteriaSet);
        final String entityID = criteriaSet.get(EntityIDCriteria.class).getEntityID();
        final UsageCriteria usageCriteria = criteriaSet.get(UsageCriteria.class);
        UsageType usage;
        if (usageCriteria != null) {
            usage = usageCriteria.getUsage();
        }
        else {
            usage = UsageType.UNSPECIFIED;
        }
        if (!this.matchUsage(this.keystoreUsage, usage)) {
            this.log.debug("Specified usage criteria {} does not match keystore usage {}", (Object)usage, (Object)this.keystoreUsage);
            this.log.debug("Can not resolve credentials from this keystore");
            return (Iterable<Credential>)Collections.emptySet();
        }
        KeyStore.PasswordProtection keyPassword = null;
        if (this.keyPasswords.containsKey(entityID)) {
            keyPassword = new KeyStore.PasswordProtection(this.keyPasswords.get(entityID).toCharArray());
        }
        KeyStore.Entry keyStoreEntry = null;
        try {
            keyStoreEntry = this.keyStore.getEntry(entityID, keyPassword);
        }
        catch (UnrecoverableEntryException e) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): " + entityID);
            this.log.error("Check for invalid keystore entityID/alias entry password");
            throw new SecurityException("Could not retrieve entry from keystore", e);
        }
        catch (GeneralSecurityException e2) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): " + entityID, (Throwable)e2);
            throw new SecurityException("Could not retrieve entry from keystore", e2);
        }
        if (keyStoreEntry == null) {
            this.log.debug("Keystore entry for entity ID (keystore alias) {} does not exist", (Object)entityID);
            return (Iterable<Credential>)Collections.emptySet();
        }
        final Credential credential = this.buildCredential(keyStoreEntry, entityID, this.keystoreUsage);
        return Collections.singleton(credential);
    }
    
    protected void checkCriteriaRequirements(final CriteriaSet criteriaSet) {
        final EntityIDCriteria entityCriteria = criteriaSet.get(EntityIDCriteria.class);
        if (entityCriteria == null) {
            this.log.error("EntityIDCriteria was not specified in the criteria set, resolution can not be attempted");
            throw new IllegalArgumentException("No EntityIDCriteria was available in criteria set");
        }
    }
    
    protected boolean matchUsage(final UsageType keyStoreUsage, final UsageType criteriaUsage) {
        return keyStoreUsage == UsageType.UNSPECIFIED || criteriaUsage == UsageType.UNSPECIFIED || keyStoreUsage == criteriaUsage;
    }
    
    protected Credential buildCredential(final KeyStore.Entry keyStoreEntry, final String entityID, final UsageType usage) throws SecurityException {
        this.log.debug("Building credential from keystore entry for entityID {}, usage type {}", (Object)entityID, (Object)usage);
        Credential credential = null;
        if (keyStoreEntry instanceof KeyStore.PrivateKeyEntry) {
            credential = this.processPrivateKeyEntry((KeyStore.PrivateKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        else if (keyStoreEntry instanceof KeyStore.TrustedCertificateEntry) {
            credential = this.processTrustedCertificateEntry((KeyStore.TrustedCertificateEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        else {
            if (!(keyStoreEntry instanceof KeyStore.SecretKeyEntry)) {
                throw new SecurityException("KeyStore entry was of an unsupported type: " + keyStoreEntry.getClass().getName());
            }
            credential = this.processSecretKeyEntry((KeyStore.SecretKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        return credential;
    }
    
    protected X509Credential processTrustedCertificateEntry(final KeyStore.TrustedCertificateEntry trustedCertEntry, final String entityID, final UsageType usage) {
        this.log.debug("Processing TrustedCertificateEntry from keystore");
        final BasicX509Credential credential = new BasicX509Credential();
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        final X509Certificate cert = (X509Certificate)trustedCertEntry.getTrustedCertificate();
        credential.setEntityCertificate(cert);
        final ArrayList<X509Certificate> certChain = new ArrayList<X509Certificate>();
        certChain.add(cert);
        credential.setEntityCertificateChain(certChain);
        return credential;
    }
    
    protected X509Credential processPrivateKeyEntry(final KeyStore.PrivateKeyEntry privateKeyEntry, final String entityID, final UsageType usage) {
        this.log.debug("Processing PrivateKeyEntry from keystore");
        final BasicX509Credential credential = new BasicX509Credential();
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        credential.setPrivateKey(privateKeyEntry.getPrivateKey());
        credential.setEntityCertificate((X509Certificate)privateKeyEntry.getCertificate());
        credential.setEntityCertificateChain(Arrays.asList((X509Certificate[])privateKeyEntry.getCertificateChain()));
        return credential;
    }
    
    protected Credential processSecretKeyEntry(final KeyStore.SecretKeyEntry secretKeyEntry, final String entityID, final UsageType usage) {
        this.log.debug("Processing SecretKeyEntry from keystore");
        final BasicCredential credential = new BasicCredential();
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        credential.setSecretKey(secretKeyEntry.getSecretKey());
        return credential;
    }
}
