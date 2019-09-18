// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;
import java.util.ArrayList;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.security.cert.X509CRL;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import java.security.KeyStore;
import org.slf4j.Logger;
import org.opensaml.xml.security.credential.BasicCredential;

public class KeyStoreX509CredentialAdapter extends BasicCredential implements X509Credential
{
    private Logger log;
    private KeyStore keyStore;
    private String credentialAlias;
    private char[] keyPassword;
    
    public KeyStoreX509CredentialAdapter(final KeyStore store, final String alias, final char[] password) {
        this.log = LoggerFactory.getLogger((Class)KeyStoreX509CredentialAdapter.class);
        this.keyStore = store;
        this.credentialAlias = alias;
        this.keyPassword = password;
    }
    
    public Collection<X509CRL> getCRLs() {
        return (Collection<X509CRL>)Collections.EMPTY_LIST;
    }
    
    public X509Certificate getEntityCertificate() {
        try {
            return (X509Certificate)this.keyStore.getCertificate(this.credentialAlias);
        }
        catch (KeyStoreException e) {
            this.log.error("Error accessing {} certificates in keystore", (Throwable)e);
            return null;
        }
    }
    
    public Collection<X509Certificate> getEntityCertificateChain() {
        List<X509Certificate> certsCollection = (List<X509Certificate>)Collections.EMPTY_LIST;
        try {
            final Certificate[] certs = this.keyStore.getCertificateChain(this.credentialAlias);
            if (certs != null) {
                certsCollection = new ArrayList<X509Certificate>(certs.length);
                Certificate[] array;
                for (int length = (array = certs).length, i = 0; i < length; ++i) {
                    final Certificate cert = array[i];
                    certsCollection.add((X509Certificate)cert);
                }
            }
        }
        catch (KeyStoreException e) {
            this.log.error("Error accessing {} certificates in keystore", (Throwable)e);
        }
        return certsCollection;
    }
    
    @Override
    public PrivateKey getPrivateKey() {
        try {
            return (PrivateKey)this.keyStore.getKey(this.credentialAlias, this.keyPassword);
        }
        catch (Exception e) {
            this.log.error("Error accessing {} private key in keystore", (Throwable)e);
            return null;
        }
    }
    
    @Override
    public PublicKey getPublicKey() {
        return this.getEntityCertificate().getPublicKey();
    }
}
