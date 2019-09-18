// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.XMLObject;
import java.util.Iterator;
import java.util.List;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.signature.RetrievalMethod;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SimpleRetrievalMethodEncryptedKeyResolver extends AbstractEncryptedKeyResolver
{
    private final Logger log;
    
    public SimpleRetrievalMethodEncryptedKeyResolver() {
        this.log = LoggerFactory.getLogger((Class)SimpleRetrievalMethodEncryptedKeyResolver.class);
    }
    
    public Iterable<EncryptedKey> resolve(final EncryptedData encryptedData) {
        final List<EncryptedKey> resolvedEncKeys = new ArrayList<EncryptedKey>();
        if (encryptedData.getKeyInfo() == null) {
            return resolvedEncKeys;
        }
        for (final RetrievalMethod rm : encryptedData.getKeyInfo().getRetrievalMethods()) {
            if (!DatatypeHelper.safeEquals(rm.getType(), "http://www.w3.org/2001/04/xmlenc#EncryptedKey")) {
                continue;
            }
            if (rm.getTransforms() != null) {
                this.log.warn("EncryptedKey RetrievalMethod has transforms, can not process");
            }
            else {
                final EncryptedKey encKey = this.dereferenceURI(rm);
                if (encKey == null) {
                    continue;
                }
                if (!this.matchRecipient(encKey.getRecipient())) {
                    continue;
                }
                resolvedEncKeys.add(encKey);
            }
        }
        return resolvedEncKeys;
    }
    
    protected EncryptedKey dereferenceURI(final RetrievalMethod rm) {
        final String uri = rm.getURI();
        if (DatatypeHelper.isEmpty(uri) || !uri.startsWith("#")) {
            this.log.warn("EncryptedKey RetrievalMethod did not contain a same-document URI reference, can not process");
            return null;
        }
        final XMLObject target = rm.resolveIDFromRoot(uri.substring(1));
        if (target == null) {
            this.log.warn("EncryptedKey RetrievalMethod URI could not be dereferenced");
            return null;
        }
        if (!(target instanceof EncryptedKey)) {
            this.log.warn("The product of dereferencing the EncryptedKey RetrievalMethod was not an EncryptedKey");
            return null;
        }
        return (EncryptedKey)target;
    }
}
