// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.PgenCounter;
import org.opensaml.xml.signature.Seed;
import org.opensaml.xml.signature.J;
import org.opensaml.xml.signature.Y;
import org.opensaml.xml.signature.G;
import org.opensaml.xml.signature.Q;
import org.opensaml.xml.signature.P;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class DSAKeyValueImpl extends AbstractValidatingXMLObject implements DSAKeyValue
{
    private P p;
    private Q q;
    private G g;
    private Y y;
    private J j;
    private Seed seed;
    private PgenCounter pgenCounter;
    
    protected DSAKeyValueImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
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
    
    public G getG() {
        return this.g;
    }
    
    public void setG(final G newG) {
        this.g = this.prepareForAssignment(this.g, newG);
    }
    
    public Y getY() {
        return this.y;
    }
    
    public void setY(final Y newY) {
        this.y = this.prepareForAssignment(this.y, newY);
    }
    
    public J getJ() {
        return this.j;
    }
    
    public void setJ(final J newJ) {
        this.j = this.prepareForAssignment(this.j, newJ);
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
        if (this.g != null) {
            children.add(this.g);
        }
        if (this.y != null) {
            children.add(this.y);
        }
        if (this.j != null) {
            children.add(this.j);
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
