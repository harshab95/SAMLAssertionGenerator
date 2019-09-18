// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Arrays;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.security.cert.X509CRL;
import java.util.Collection;
import org.opensaml.xml.util.DatatypeHelper;
import javax.net.ssl.X509KeyManager;
import org.opensaml.xml.security.credential.BasicCredential;

public class X509KeyManagerX509CredentialAdapter extends BasicCredential implements X509Credential
{
    private String credentialAlias;
    private X509KeyManager keyManager;
    
    public X509KeyManagerX509CredentialAdapter(final X509KeyManager manager, final String alias) {
        if (manager == null) {
            throw new IllegalArgumentException("Key manager may not be null");
        }
        this.keyManager = manager;
        this.credentialAlias = DatatypeHelper.safeTrimOrNullString(alias);
        if (this.credentialAlias == null) {
            throw new IllegalArgumentException("Entity alias may not be null");
        }
    }
    
    public Collection<X509CRL> getCRLs() {
        return (Collection<X509CRL>)Collections.EMPTY_LIST;
    }
    
    public X509Certificate getEntityCertificate() {
        final X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
        if (certs != null && certs.length > 0) {
            return certs[0];
        }
        return null;
    }
    
    public Collection<X509Certificate> getEntityCertificateChain() {
        final X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
        if (certs != null && certs.length > 0) {
            return Arrays.asList(certs);
        }
        return null;
    }
    
    @Override
    public PrivateKey getPrivateKey() {
        return this.keyManager.getPrivateKey(this.credentialAlias);
    }
    
    @Override
    public PublicKey getPublicKey() {
        return this.getEntityCertificate().getPublicKey();
    }
}
