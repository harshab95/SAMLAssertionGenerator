// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.AbstractSet;

public class ClassIndexedSet<T> extends AbstractSet<T> implements Set<T>
{
    private HashSet<T> set;
    private HashMap<Class<? extends T>, T> index;
    
    public ClassIndexedSet() {
        this.set = new HashSet<T>();
        this.index = new HashMap<Class<? extends T>, T>();
    }
    
    @Override
    public boolean add(final T o) {
        return this.add(o, false);
    }
    
    public boolean add(final T o, final boolean replace) throws NullPointerException, IllegalArgumentException {
        if (o == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        boolean replacing = false;
        final Class<? extends T> indexClass = this.getIndexClass(o);
        final T existing = this.get((Class<T>)indexClass);
        if (existing != null) {
            replacing = true;
            if (!replace) {
                throw new IllegalArgumentException("Set already contains a member of index class " + indexClass.getName());
            }
            this.remove(existing);
        }
        this.index.put(indexClass, o);
        this.set.add(o);
        return replacing;
    }
    
    @Override
    public void clear() {
        this.set.clear();
        this.index.clear();
    }
    
    @Override
    public boolean remove(final Object o) {
        if (this.set.contains(o)) {
            this.removeFromIndex(o);
            this.set.remove(o);
            return true;
        }
        return false;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ClassIndexedSetIterator(this, this.set.iterator());
    }
    
    @Override
    public int size() {
        return this.set.size();
    }
    
    public boolean contains(final Class<? extends T> clazz) {
        return this.get(clazz) != null;
    }
    
    public <X extends T> X get(final Class<X> clazz) {
        return (X)this.index.get(clazz);
    }
    
    protected Class<? extends T> getIndexClass(final Object o) {
        return (Class<? extends T>)o.getClass();
    }
    
    private void removeFromIndex(final T o) {
        this.index.remove(this.getIndexClass(o));
    }
    
    protected class ClassIndexedSetIterator implements Iterator<T>
    {
        private ClassIndexedSet<T> set;
        private Iterator<T> iterator;
        private boolean nextCalled;
        private boolean removeStateValid;
        private T current;
        
        protected ClassIndexedSetIterator(final ClassIndexedSet<T> parentSet, final Iterator<T> parentIterator) {
            this.set = parentSet;
            this.iterator = parentIterator;
            this.current = null;
            this.nextCalled = false;
            this.removeStateValid = false;
        }
        
        public boolean hasNext() {
            return this.iterator.hasNext();
        }
        
        public T next() {
            this.current = this.iterator.next();
            this.nextCalled = true;
            this.removeStateValid = true;
            return this.current;
        }
        
        public void remove() {
            if (!this.nextCalled) {
                throw new IllegalStateException("remove() was called before calling next()");
            }
            if (!this.removeStateValid) {
                throw new IllegalStateException("remove() has already been called since the last call to next()");
            }
            this.iterator.remove();
            this.set.removeFromIndex(this.current);
            this.removeStateValid = false;
        }
    }
}
