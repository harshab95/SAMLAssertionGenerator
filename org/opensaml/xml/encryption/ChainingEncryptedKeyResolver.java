// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

public class ChainingEncryptedKeyResolver extends AbstractEncryptedKeyResolver
{
    private final List<EncryptedKeyResolver> resolvers;
    private final Logger log;
    
    public ChainingEncryptedKeyResolver() {
        this.log = LoggerFactory.getLogger((Class)ChainingEncryptedKeyResolver.class);
        this.resolvers = new ArrayList<EncryptedKeyResolver>();
    }
    
    public List<EncryptedKeyResolver> getResolverChain() {
        return this.resolvers;
    }
    
    public Iterable<EncryptedKey> resolve(final EncryptedData encryptedData) {
        if (this.resolvers.isEmpty()) {
            this.log.warn("Chaining encrypted key resolver resolution was attempted with an empty resolver chain");
            throw new IllegalStateException("The resolver chain is empty");
        }
        return new ChainingIterable(this, encryptedData);
    }
    
    public class ChainingIterator implements Iterator<EncryptedKey>
    {
        private final Logger log;
        private ChainingEncryptedKeyResolver parent;
        private EncryptedData encryptedData;
        private Iterator<EncryptedKeyResolver> resolverIterator;
        private Iterator<EncryptedKey> keyIterator;
        private EncryptedKeyResolver currentResolver;
        private EncryptedKey nextKey;
        
        public ChainingIterator(final ChainingEncryptedKeyResolver resolver, final EncryptedData encData) {
            this.log = LoggerFactory.getLogger((Class)ChainingIterator.class);
            this.parent = resolver;
            this.encryptedData = encData;
            this.resolverIterator = this.parent.getResolverChain().iterator();
            this.keyIterator = this.getNextKeyIterator();
            this.nextKey = null;
        }
        
        public boolean hasNext() {
            if (this.nextKey != null) {
                return true;
            }
            this.nextKey = this.getNextKey();
            return this.nextKey != null;
        }
        
        public EncryptedKey next() {
            if (this.nextKey != null) {
                final EncryptedKey tempKey = this.nextKey;
                this.nextKey = null;
                return tempKey;
            }
            final EncryptedKey tempKey = this.getNextKey();
            if (tempKey != null) {
                return tempKey;
            }
            throw new NoSuchElementException("No more EncryptedKey elements are available");
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
        }
        
        private Iterator<EncryptedKey> getNextKeyIterator() {
            if (this.resolverIterator.hasNext()) {
                this.currentResolver = this.resolverIterator.next();
                this.log.debug("Getting key iterator from next resolver: {}", (Object)this.currentResolver.getClass().toString());
                return this.currentResolver.resolve(this.encryptedData).iterator();
            }
            this.log.debug("No more resolvers available in the resolver chain");
            this.currentResolver = null;
            return null;
        }
        
        private EncryptedKey getNextKey() {
            if (this.keyIterator != null) {
                while (this.keyIterator.hasNext()) {
                    final EncryptedKey tempKey = this.keyIterator.next();
                    if (this.parent.matchRecipient(tempKey.getRecipient())) {
                        this.log.debug("Found matching encrypted key: {}", (Object)tempKey.toString());
                        return tempKey;
                    }
                }
            }
            this.keyIterator = this.getNextKeyIterator();
            while (this.keyIterator != null) {
                while (this.keyIterator.hasNext()) {
                    final EncryptedKey tempKey = this.keyIterator.next();
                    if (this.parent.matchRecipient(tempKey.getRecipient())) {
                        this.log.debug("Found matching encrypted key: {}", (Object)tempKey.toString());
                        return tempKey;
                    }
                }
                this.keyIterator = this.getNextKeyIterator();
            }
            return null;
        }
    }
    
    public class ChainingIterable implements Iterable<EncryptedKey>
    {
        private ChainingEncryptedKeyResolver parent;
        private EncryptedData encryptedData;
        
        public ChainingIterable(final ChainingEncryptedKeyResolver resolver, final EncryptedData encData) {
            this.parent = resolver;
            this.encryptedData = encData;
        }
        
        public Iterator<EncryptedKey> iterator() {
            return new ChainingIterator(this.parent, this.encryptedData);
        }
    }
}
