// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.credential.Credential;
import java.util.Iterator;
import java.security.Key;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ExplicitKeyTrustEvaluator
{
    private final Logger log;
    
    public ExplicitKeyTrustEvaluator() {
        this.log = LoggerFactory.getLogger((Class)ExplicitKeyTrustEvaluator.class);
    }
    
    public boolean validate(final Key untrustedKey, final Key trustedKey) {
        return untrustedKey.equals(trustedKey);
    }
    
    public boolean validate(final Key untrustedKey, final Iterable<Key> trustedKeys) {
        for (final Key trustedKey : trustedKeys) {
            if (untrustedKey.equals(trustedKey)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validate(final Credential untrustedCredential, final Credential trustedCredential) {
        Key untrustedKey = null;
        Key trustedKey = null;
        if (untrustedCredential.getPublicKey() != null) {
            untrustedKey = untrustedCredential.getPublicKey();
            trustedKey = trustedCredential.getPublicKey();
        }
        else {
            untrustedKey = untrustedCredential.getSecretKey();
            trustedKey = trustedCredential.getSecretKey();
        }
        if (untrustedKey == null) {
            this.log.debug("Untrusted credential contained no key, unable to evaluate");
            return false;
        }
        if (trustedKey == null) {
            this.log.debug("Trusted credential contained no key of the appropriate type, unable to evaluate");
            return false;
        }
        if (this.validate(untrustedKey, trustedKey)) {
            this.log.debug("Successfully validated untrusted credential against trusted key");
            return true;
        }
        this.log.debug("Failed to validate untrusted credential against trusted key");
        return false;
    }
    
    public boolean validate(final Credential untrustedCredential, final Iterable<Credential> trustedCredentials) {
        for (final Credential trustedCredential : trustedCredentials) {
            if (this.validate(untrustedCredential, trustedCredential)) {
                return true;
            }
        }
        return false;
    }
}
