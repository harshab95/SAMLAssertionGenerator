// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class InlineEncryptedKeyResolver extends AbstractEncryptedKeyResolver
{
    public Iterable<EncryptedKey> resolve(final EncryptedData encryptedData) {
        final List<EncryptedKey> resolvedEncKeys = new ArrayList<EncryptedKey>();
        if (encryptedData.getKeyInfo() == null) {
            return resolvedEncKeys;
        }
        for (final EncryptedKey encKey : encryptedData.getKeyInfo().getEncryptedKeys()) {
            if (this.matchRecipient(encKey.getRecipient())) {
                resolvedEncKeys.add(encKey);
            }
        }
        return resolvedEncKeys;
    }
}
