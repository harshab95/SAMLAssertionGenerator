// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.List;

public interface EncryptedKeyResolver
{
    Iterable<EncryptedKey> resolve(final EncryptedData p0);
    
    List<String> getRecipients();
}
