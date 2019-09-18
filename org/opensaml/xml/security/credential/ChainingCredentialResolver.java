// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.util.NoSuchElementException;
import java.util.Iterator;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;

public class ChainingCredentialResolver extends AbstractCredentialResolver
{
    private final Logger log;
    private List<CredentialResolver> resolvers;
    
    public ChainingCredentialResolver() {
        this.log = LoggerFactory.getLogger((Class)ChainingCredentialResolver.class);
        this.resolvers = new ArrayList<CredentialResolver>();
    }
    
    public List<CredentialResolver> getResolverChain() {
        return this.resolvers;
    }
    
    @Override
    public Iterable<Credential> resolve(final CriteriaSet criteriaSet) throws SecurityException {
        if (this.resolvers.isEmpty()) {
            this.log.warn("Chaining credential resolver resolution was attempted with an empty resolver chain");
            throw new IllegalStateException("The resolver chain is empty");
        }
        return new CredentialIterable(this, criteriaSet);
    }
    
    public class CredentialIterator implements Iterator<Credential>
    {
        private final Logger log;
        private ChainingCredentialResolver parent;
        private CriteriaSet critSet;
        private Iterator<CredentialResolver> resolverIterator;
        private Iterator<Credential> credentialIterator;
        private CredentialResolver currentResolver;
        private Credential nextCredential;
        
        public CredentialIterator(final ChainingCredentialResolver resolver, final CriteriaSet criteriaSet) {
            this.log = LoggerFactory.getLogger((Class)CredentialIterator.class);
            this.parent = resolver;
            this.critSet = criteriaSet;
            this.resolverIterator = this.parent.getResolverChain().iterator();
            this.credentialIterator = this.getNextCredentialIterator();
            this.nextCredential = null;
        }
        
        public boolean hasNext() {
            if (this.nextCredential != null) {
                return true;
            }
            this.nextCredential = this.getNextCredential();
            return this.nextCredential != null;
        }
        
        public Credential next() {
            if (this.nextCredential != null) {
                final Credential tempCred = this.nextCredential;
                this.nextCredential = null;
                return tempCred;
            }
            final Credential tempCred = this.getNextCredential();
            if (tempCred != null) {
                return tempCred;
            }
            throw new NoSuchElementException("No more Credential elements are available");
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
        }
        
        private Iterator<Credential> getNextCredentialIterator() {
            while (this.resolverIterator.hasNext()) {
                this.currentResolver = this.resolverIterator.next();
                this.log.debug("Getting credential iterator from next resolver in chain: {}", (Object)this.currentResolver.getClass().toString());
                try {
                    return this.currentResolver.resolve(this.critSet).iterator();
                }
                catch (SecurityException e) {
                    this.log.error(String.format("Error resolving credentials from chaining resolver member '%s'", this.currentResolver.getClass().getName()), (Throwable)e);
                    if (!this.resolverIterator.hasNext()) {
                        continue;
                    }
                    this.log.error("Will attempt to resolve credentials from next member of resolver chain");
                }
            }
            this.log.debug("No more credential resolvers available in the resolver chain");
            this.currentResolver = null;
            return null;
        }
        
        private Credential getNextCredential() {
            if (this.credentialIterator != null && this.credentialIterator.hasNext()) {
                return this.credentialIterator.next();
            }
            this.credentialIterator = this.getNextCredentialIterator();
            while (this.credentialIterator != null) {
                if (this.credentialIterator.hasNext()) {
                    return this.credentialIterator.next();
                }
                this.credentialIterator = this.getNextCredentialIterator();
            }
            return null;
        }
    }
    
    public class CredentialIterable implements Iterable<Credential>
    {
        private ChainingCredentialResolver parent;
        private CriteriaSet critSet;
        
        public CredentialIterable(final ChainingCredentialResolver resolver, final CriteriaSet criteriaSet) {
            this.parent = resolver;
            this.critSet = criteriaSet;
        }
        
        public Iterator<Credential> iterator() {
            return new CredentialIterator(this.parent, this.critSet);
        }
    }
}
