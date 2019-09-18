// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.security.PublicKey;
import org.opensaml.xml.security.credential.Credential;
import java.security.cert.X509CRL;
import java.util.Collection;
import java.security.cert.X509Certificate;
import org.opensaml.xml.security.credential.BasicCredential;

public class BasicX509Credential extends BasicCredential implements X509Credential
{
    private X509Certificate entityCert;
    private Collection<X509Certificate> entityCertChain;
    private Collection<X509CRL> crls;
    
    @Override
    public Class<? extends Credential> getCredentialType() {
        return X509Credential.class;
    }
    
    public Collection<X509CRL> getCRLs() {
        return this.crls;
    }
    
    public void setCRLs(final Collection<X509CRL> newCRLs) {
        this.crls = newCRLs;
    }
    
    public X509Certificate getEntityCertificate() {
        return this.entityCert;
    }
    
    public void setEntityCertificate(final X509Certificate cert) {
        this.entityCert = cert;
        if (cert != null) {
            this.setPublicKey(cert.getPublicKey());
        }
        else {
            this.setPublicKey(null);
        }
    }
    
    public Collection<X509Certificate> getEntityCertificateChain() {
        if (this.entityCertChain == null && this.entityCert != null) {
            final HashSet<X509Certificate> constructedChain = new HashSet<X509Certificate>(5);
            constructedChain.add(this.entityCert);
            return constructedChain;
        }
        return this.entityCertChain;
    }
    
    public void setEntityCertificateChain(final Collection<X509Certificate> certs) {
        this.entityCertChain = new ArrayList<X509Certificate>(certs);
    }
    
    @Override
    public void setPublicKey(final PublicKey key) {
        if (this.entityCert != null && !this.entityCert.getPublicKey().equals(key)) {
            throw new IllegalArgumentException("X509Credential already contains a certificate with a different public key");
        }
        super.setPublicKey(key);
    }
    
    @Override
    public void setSecretKey(final SecretKey key) {
        if (key != null) {
            throw new UnsupportedOperationException("Secret (symmetric) key may not be set on an X509Credential instance");
        }
    }
    
    @Override
    public SecretKey getSecretKey() {
        return null;
    }
}
