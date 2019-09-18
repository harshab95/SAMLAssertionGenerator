// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.security.credential.CredentialContext;

public class XMLSignatureCredentialContext implements CredentialContext
{
    private Signature sig;
    
    public XMLSignatureCredentialContext(final Signature signature) {
        this.sig = signature;
    }
    
    public Signature getSignature() {
        return this.sig;
    }
}
