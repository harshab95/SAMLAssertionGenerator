// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.cert.X509CRL;
import java.util.Collection;
import java.security.cert.X509Certificate;
import org.opensaml.xml.security.credential.Credential;

public interface X509Credential extends Credential
{
    X509Certificate getEntityCertificate();
    
    Collection<X509Certificate> getEntityCertificateChain();
    
    Collection<X509CRL> getCRLs();
}
