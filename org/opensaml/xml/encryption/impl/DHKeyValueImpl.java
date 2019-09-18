// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.encryption.PgenCounter;
import org.opensaml.xml.encryption.Seed;
import org.opensaml.xml.encryption.Public;
import org.opensaml.xml.encryption.Generator;
import org.opensaml.xml.encryption.Q;
import org.opensaml.xml.encryption.P;
import org.opensaml.xml.encryption.DHKeyValue;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class DHKeyValueImpl extends AbstractValidatingXMLObject implements DHKeyValue
{
    private P p;
    private Q q;
    private Generator generator;
    private Public publicChild;
    private Seed seed;
    private PgenCounter pgenCounter;
    
    protected DHKeyValueImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public P getP() {
        return this.p;
    }
    
    public void setP(final P newP) {
        this.p = this.prepareForAssignment(this.p, newP);
    }
    
    public Q getQ() {
        return this.q;
    }
    
    public void setQ(final Q newQ) {
        this.q = this.prepareForAssignment(this.q, newQ);
    }
    
    public Generator getGenerator() {
        return this.generator;
    }
    
    public void setGenerator(final Generator newGenerator) {
        this.generator = this.prepareForAssignment(this.generator, newGenerator);
    }
    
    public Public getPublic() {
        return this.publicChild;
    }
    
    public void setPublic(final Public newPublic) {
        this.publicChild = this.prepareForAssignment(this.publicChild, newPublic);
    }
    
    public Seed getSeed() {
        return this.seed;
    }
    
    public void setSeed(final Seed newSeed) {
        this.seed = this.prepareForAssignment(this.seed, newSeed);
    }
    
    public PgenCounter getPgenCounter() {
        return this.pgenCounter;
    }
    
    public void setPgenCounter(final PgenCounter newPgenCounter) {
        this.pgenCounter = this.prepareForAssignment(this.pgenCounter, newPgenCounter);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.p != null) {
            children.add(this.p);
        }
        if (this.q != null) {
            children.add(this.q);
        }
        if (this.generator != null) {
            children.add(this.generator);
        }
        if (this.publicChild != null) {
            children.add(this.publicChild);
        }
        if (this.seed != null) {
            children.add(this.seed);
        }
        if (this.pgenCounter != null) {
            children.add(this.pgenCounter);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
