// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.slf4j.Logger;

public abstract class AbstractWrappedSingletonFactory<Input, Output> extends AbstractSingletonFactory<Input, Output>
{
    private final Logger log;
    private WeakHashMap<Input, WeakReference<Output>> map;
    private HashSet<Output> outputSet;
    private boolean explicitRelease;
    
    public AbstractWrappedSingletonFactory() {
        this(false);
    }
    
    public AbstractWrappedSingletonFactory(final boolean requireExplicitRelease) {
        this.log = LoggerFactory.getLogger((Class)AbstractWrappedSingletonFactory.class);
        this.map = new WeakHashMap<Input, WeakReference<Output>>();
        this.explicitRelease = requireExplicitRelease;
        this.outputSet = new HashSet<Output>();
    }
    
    @Override
    public synchronized Output getInstance(final Input input) {
        final Output output = super.getInstance(input);
        if (this.explicitRelease && output != null) {
            this.log.trace("Explicit release was indicated, registering output instance to inhibit garbage collection");
            this.register(output);
        }
        return output;
    }
    
    public boolean isRequireExplicitRelease() {
        return this.explicitRelease;
    }
    
    public synchronized void release(final Output output) {
        this.outputSet.remove(output);
    }
    
    public synchronized void releaseAll() {
        this.outputSet.clear();
    }
    
    protected synchronized void register(final Output output) {
        this.outputSet.add(output);
    }
    
    @Override
    protected synchronized Output get(final Input input) {
        final WeakReference<Output> outputRef = this.map.get(input);
        if (outputRef != null) {
            this.log.trace("Input key mapped to a non-null WeakReference");
            if (outputRef.get() != null) {
                this.log.trace("WeakReference referent was non-null, returning referent");
                return outputRef.get();
            }
            this.log.trace("WeakReference referent was null, removing WeakReference entry from map");
            this.map.remove(input);
        }
        return null;
    }
    
    @Override
    protected synchronized void put(final Input input, final Output output) {
        this.map.put(input, new WeakReference<Output>(output));
    }
}
