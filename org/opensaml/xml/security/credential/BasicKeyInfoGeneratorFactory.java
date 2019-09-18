// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.util.Iterator;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.signature.impl.KeyInfoBuilder;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;

public class BasicKeyInfoGeneratorFactory implements KeyInfoGeneratorFactory
{
    private BasicOptions options;
    
    public BasicKeyInfoGeneratorFactory() {
        this.options = this.newOptions();
    }
    
    public Class<? extends Credential> getCredentialType() {
        return Credential.class;
    }
    
    public boolean handles(final Credential credential) {
        return true;
    }
    
    public KeyInfoGenerator newInstance() {
        final BasicOptions newOptions = this.options.clone();
        return new BasicKeyInfoGenerator(newOptions);
    }
    
    public boolean emitEntityIDAsKeyName() {
        return this.options.emitEntityIDAsKeyName;
    }
    
    public void setEmitEntityIDAsKeyName(final boolean newValue) {
        BasicOptions.access$4(this.options, newValue);
    }
    
    public boolean emitKeyNames() {
        return this.options.emitKeyNames;
    }
    
    public void setEmitKeyNames(final boolean newValue) {
        BasicOptions.access$5(this.options, newValue);
    }
    
    public boolean emitPublicKeyValue() {
        return this.options.emitPublicKeyValue;
    }
    
    public void setEmitPublicKeyValue(final boolean newValue) {
        BasicOptions.access$6(this.options, newValue);
    }
    
    protected BasicOptions newOptions() {
        return new BasicOptions();
    }
    
    protected BasicOptions getOptions() {
        return this.options;
    }
    
    protected class BasicOptions implements Cloneable
    {
        private boolean emitKeyNames;
        private boolean emitEntityIDAsKeyName;
        private boolean emitPublicKeyValue;
        
        @Override
        protected BasicOptions clone() {
            try {
                return (BasicOptions)super.clone();
            }
            catch (CloneNotSupportedException e) {
                return null;
            }
        }
        
        static /* synthetic */ void access$4(final BasicOptions basicOptions, final boolean emitEntityIDAsKeyName) {
            basicOptions.emitEntityIDAsKeyName = emitEntityIDAsKeyName;
        }
        
        static /* synthetic */ void access$5(final BasicOptions basicOptions, final boolean emitKeyNames) {
            basicOptions.emitKeyNames = emitKeyNames;
        }
        
        static /* synthetic */ void access$6(final BasicOptions basicOptions, final boolean emitPublicKeyValue) {
            basicOptions.emitPublicKeyValue = emitPublicKeyValue;
        }
    }
    
    public class BasicKeyInfoGenerator implements KeyInfoGenerator
    {
        private BasicOptions options;
        private KeyInfoBuilder keyInfoBuilder;
        
        protected BasicKeyInfoGenerator(final BasicOptions newOptions) {
            this.options = newOptions;
            this.keyInfoBuilder = (KeyInfoBuilder)Configuration.getBuilderFactory().getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
        }
        
        public KeyInfo generate(final Credential credential) throws SecurityException {
            final KeyInfo keyInfo = this.keyInfoBuilder.buildObject();
            this.processKeyNames(keyInfo, credential);
            this.processEntityID(keyInfo, credential);
            this.processPublicKey(keyInfo, credential);
            final List<XMLObject> children = keyInfo.getOrderedChildren();
            if (children != null && children.size() > 0) {
                return keyInfo;
            }
            return null;
        }
        
        protected void processKeyNames(final KeyInfo keyInfo, final Credential credential) {
            if (this.options.emitKeyNames) {
                for (final String keyNameValue : credential.getKeyNames()) {
                    if (!DatatypeHelper.isEmpty(keyNameValue)) {
                        KeyInfoHelper.addKeyName(keyInfo, keyNameValue);
                    }
                }
            }
        }
        
        protected void processEntityID(final KeyInfo keyInfo, final Credential credential) {
            if (this.options.emitEntityIDAsKeyName) {
                final String keyNameValue = credential.getEntityId();
                if (!DatatypeHelper.isEmpty(keyNameValue)) {
                    KeyInfoHelper.addKeyName(keyInfo, keyNameValue);
                }
            }
        }
        
        protected void processPublicKey(final KeyInfo keyInfo, final Credential credential) {
            if (this.options.emitPublicKeyValue && credential.getPublicKey() != null) {
                KeyInfoHelper.addPublicKey(keyInfo, credential.getPublicKey());
            }
        }
    }
}
