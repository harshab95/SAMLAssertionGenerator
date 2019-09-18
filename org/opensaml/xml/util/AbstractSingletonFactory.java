// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class AbstractSingletonFactory<Input, Output> implements SingletonFactory<Input, Output>
{
    private final Logger log;
    
    public AbstractSingletonFactory() {
        this.log = LoggerFactory.getLogger((Class)AbstractSingletonFactory.class);
    }
    
    public synchronized Output getInstance(final Input input) {
        Output output = this.get(input);
        if (output != null) {
            this.log.trace("Input key mapped to a non-null value, returning output");
            return output;
        }
        this.log.trace("Input key mapped to a null value");
        this.log.trace("Creating new output instance and inserting to factory map");
        output = this.createNewInstance(input);
        if (output == null) {
            this.log.error("New output instance was not created");
            return null;
        }
        this.put(input, output);
        return output;
    }
    
    protected abstract Output get(final Input p0);
    
    protected abstract void put(final Input p0, final Output p1);
    
    protected abstract Output createNewInstance(final Input p0);
}
