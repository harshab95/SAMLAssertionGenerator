// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.WeakHashMap;

public abstract class AbstractSimpleSingletonFactory<Input, Output> extends AbstractSingletonFactory<Input, Output>
{
    private WeakHashMap<Input, Output> map;
    
    public AbstractSimpleSingletonFactory() {
        this.map = new WeakHashMap<Input, Output>();
    }
    
    @Override
    protected synchronized Output get(final Input input) {
        return this.map.get(input);
    }
    
    @Override
    protected synchronized void put(final Input input, final Output output) {
        this.map.put(input, output);
    }
}
