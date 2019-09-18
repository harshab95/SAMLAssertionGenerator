// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.Iterator;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.util.DatatypeHelper;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEncryptedKeyResolver implements EncryptedKeyResolver
{
    private final List<String> recipients;
    
    public AbstractEncryptedKeyResolver() {
        this.recipients = new ArrayList<String>();
    }
    
    public List<String> getRecipients() {
        return this.recipients;
    }
    
    protected boolean matchRecipient(final String recipient) {
        final String trimmedRecipient = DatatypeHelper.safeTrimOrNullString(recipient);
        return trimmedRecipient == null || this.recipients.isEmpty() || this.recipients.contains(trimmedRecipient);
    }
    
    protected boolean matchCarriedKeyName(final EncryptedData encryptedData, final EncryptedKey encryptedKey) {
        if (encryptedKey.getCarriedKeyName() == null || DatatypeHelper.isEmpty(encryptedKey.getCarriedKeyName().getValue())) {
            return true;
        }
        if (encryptedData.getKeyInfo() == null || encryptedData.getKeyInfo().getKeyNames().isEmpty()) {
            return false;
        }
        final String keyCarriedKeyName = encryptedKey.getCarriedKeyName().getValue();
        final List<String> dataKeyNames = KeyInfoHelper.getKeyNames(encryptedData.getKeyInfo());
        return dataKeyNames.contains(keyCarriedKeyName);
    }
    
    protected boolean matchDataReference(final EncryptedData encryptedData, final EncryptedKey encryptedKey) {
        if (encryptedKey.getReferenceList() == null || encryptedKey.getReferenceList().getDataReferences().isEmpty()) {
            return true;
        }
        if (DatatypeHelper.isEmpty(encryptedData.getID())) {
            return false;
        }
        final List<DataReference> drlist = encryptedKey.getReferenceList().getDataReferences();
        for (final DataReference dr : drlist) {
            if (!DatatypeHelper.isEmpty(dr.getURI())) {
                if (!dr.getURI().startsWith("#")) {
                    continue;
                }
                if (dr.resolveIDFromRoot(dr.getURI().substring(1)) == encryptedData) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
