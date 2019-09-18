// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class IndexingObjectStore<T>
{
    private ReadWriteLock rwLock;
    private Map<String, StoredObjectWrapper> objectStore;
    
    public IndexingObjectStore() {
        this.rwLock = new ReentrantReadWriteLock();
        this.objectStore = new LazyMap<String, StoredObjectWrapper>();
    }
    
    public void clear() {
        final Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            this.objectStore.clear();
        }
        finally {
            writeLock.unlock();
        }
        writeLock.unlock();
    }
    
    public boolean contains(final String index) {
        final Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            return this.objectStore.containsKey(index);
        }
        finally {
            readLock.unlock();
        }
    }
    
    public boolean isEmpty() {
        return this.objectStore.isEmpty();
    }
    
    public String put(final T object) {
        if (object == null) {
            return null;
        }
        final Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            final String index = Integer.toString(object.hashCode());
            StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper == null) {
                objectWrapper = new StoredObjectWrapper(object);
                this.objectStore.put(index, objectWrapper);
            }
            objectWrapper.incremementReferenceCount();
            return index;
        }
        finally {
            writeLock.unlock();
        }
    }
    
    public T get(final String index) {
        if (index == null) {
            return null;
        }
        final Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            final StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper != null) {
                return objectWrapper.getObject();
            }
            return null;
        }
        finally {
            readLock.unlock();
        }
    }
    
    public void remove(final String index) {
        if (index == null) {
            return;
        }
        final Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            final StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper != null) {
                objectWrapper.decremementReferenceCount();
                if (objectWrapper.getReferenceCount() == 0) {
                    this.objectStore.remove(index);
                }
            }
        }
        finally {
            writeLock.unlock();
        }
        writeLock.unlock();
    }
    
    public int size() {
        return this.objectStore.size();
    }
    
    private class StoredObjectWrapper
    {
        private T object;
        private int referenceCount;
        
        public StoredObjectWrapper(final T wrappedObject) {
            this.object = wrappedObject;
            this.referenceCount = 0;
        }
        
        public T getObject() {
            return this.object;
        }
        
        public int getReferenceCount() {
            return this.referenceCount;
        }
        
        public void incremementReferenceCount() {
            ++this.referenceCount;
        }
        
        public void decremementReferenceCount() {
            --this.referenceCount;
        }
    }
}
