// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.Configuration;
import org.w3c.dom.Element;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.signature.KeyInfo;

public class StaticKeyInfoGenerator implements KeyInfoGenerator
{
    private KeyInfo keyInfo;
    private Unmarshaller keyInfoUnmarshaller;
    private Marshaller keyInfoMarshaller;
    
    public StaticKeyInfoGenerator(final KeyInfo newKeyInfo) {
        this.setKeyInfo(newKeyInfo);
    }
    
    public KeyInfo generate(final Credential credential) throws SecurityException {
        if (this.keyInfo.getParent() == null) {
            return this.keyInfo;
        }
        return this.clone(this.keyInfo);
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
    
    public void setKeyInfo(final KeyInfo newKeyInfo) {
        if (newKeyInfo == null) {
            throw new IllegalArgumentException("KeyInfo may not be null");
        }
        this.keyInfo = newKeyInfo;
    }
    
    private KeyInfo clone(final KeyInfo origKeyInfo) throws SecurityException {
        final Element origDOM = origKeyInfo.getDOM();
        if (origDOM == null) {
            try {
                this.getMarshaller().marshall(origKeyInfo);
            }
            catch (MarshallingException e) {
                throw new SecurityException("Error marshalling the original KeyInfo during cloning", e);
            }
        }
        KeyInfo newKeyInfo = null;
        try {
            newKeyInfo = (KeyInfo)this.getUnmarshaller().unmarshall(origKeyInfo.getDOM());
        }
        catch (UnmarshallingException e2) {
            throw new SecurityException("Error unmarshalling the new KeyInfo during cloning", e2);
        }
        if (origDOM == null) {
            origKeyInfo.releaseChildrenDOM(true);
            origKeyInfo.releaseDOM();
        }
        else {
            newKeyInfo.releaseChildrenDOM(true);
            newKeyInfo.releaseDOM();
        }
        return newKeyInfo;
    }
    
    private Marshaller getMarshaller() throws SecurityException {
        if (this.keyInfoMarshaller != null) {
            return this.keyInfoMarshaller;
        }
        this.keyInfoMarshaller = Configuration.getMarshallerFactory().getMarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
        if (this.keyInfoMarshaller == null) {
            throw new SecurityException("Could not obtain KeyInfo marshaller from the configuration");
        }
        return this.keyInfoMarshaller;
    }
    
    private Unmarshaller getUnmarshaller() throws SecurityException {
        if (this.keyInfoUnmarshaller != null) {
            return this.keyInfoUnmarshaller;
        }
        this.keyInfoUnmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
        if (this.keyInfoUnmarshaller == null) {
            throw new SecurityException("Could not obtain KeyInfo unmarshaller from the configuration");
        }
        return this.keyInfoUnmarshaller;
    }
}
