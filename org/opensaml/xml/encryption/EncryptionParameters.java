// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.credential.Credential;

public class EncryptionParameters
{
    private Credential encryptionCredential;
    private String algorithm;
    private KeyInfoGenerator keyInfoGenerator;
    
    public EncryptionParameters() {
        this.setAlgorithm("http://www.w3.org/2001/04/xmlenc#aes256-cbc");
    }
    
    public String getAlgorithm() {
        return this.algorithm;
    }
    
    public void setAlgorithm(final String newAlgorithm) {
        this.algorithm = newAlgorithm;
    }
    
    public Credential getEncryptionCredential() {
        return this.encryptionCredential;
    }
    
    public void setEncryptionCredential(final Credential newEncryptionCredential) {
        this.encryptionCredential = newEncryptionCredential;
    }
    
    public KeyInfoGenerator getKeyInfoGenerator() {
        return this.keyInfoGenerator;
    }
    
    public void setKeyInfoGenerator(final KeyInfoGenerator newKeyInfoGenerator) {
        this.keyInfoGenerator = newKeyInfoGenerator;
    }
}
