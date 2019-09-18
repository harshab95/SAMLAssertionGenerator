// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

public class KeyEncryptionParameters extends EncryptionParameters
{
    private String recipient;
    
    public KeyEncryptionParameters() {
        this.setAlgorithm(null);
    }
    
    public String getRecipient() {
        return this.recipient;
    }
    
    public void setRecipient(final String newRecipient) {
        this.recipient = newRecipient;
    }
}
