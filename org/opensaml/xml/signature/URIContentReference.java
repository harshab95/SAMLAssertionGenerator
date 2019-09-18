// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import java.util.Iterator;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.signature.XMLSignature;
import java.util.LinkedList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;

public class URIContentReference implements ContentReference
{
    private final Logger log;
    private String referenceID;
    private String digestAlgorithm;
    private List<String> transforms;
    
    public URIContentReference(final String referenceID) {
        this.log = LoggerFactory.getLogger((Class)URIContentReference.class);
        this.referenceID = referenceID;
        this.transforms = new LinkedList<String>();
    }
    
    public List<String> getTransforms() {
        return this.transforms;
    }
    
    public String getDigestAlgorithm() {
        return this.digestAlgorithm;
    }
    
    public void setDigestAlgorithm(final String newAlgorithm) {
        this.digestAlgorithm = newAlgorithm;
    }
    
    public void createReference(final XMLSignature signature) {
        try {
            final Transforms dsigTransforms = new Transforms(signature.getDocument());
            for (final String transform : this.transforms) {
                dsigTransforms.addTransform(transform);
            }
            signature.addDocument(this.referenceID, dsigTransforms, this.digestAlgorithm);
        }
        catch (Exception e) {
            this.log.error("Error while adding content reference", (Throwable)e);
        }
    }
}
