// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.validation;

import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import java.util.Collections;
import org.opensaml.xml.util.LazyList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;
import org.opensaml.xml.AbstractXMLObject;

public abstract class AbstractValidatingXMLObject extends AbstractXMLObject implements ValidatingXMLObject
{
    private final Logger log;
    private List<Validator> validators;
    
    protected AbstractValidatingXMLObject(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.log = LoggerFactory.getLogger((Class)AbstractValidatingXMLObject.class);
        this.validators = new LazyList<Validator>();
    }
    
    public List<Validator> getValidators() {
        if (this.validators.size() > 0) {
            return (List<Validator>)Collections.unmodifiableList((List<? extends Validator>)this.validators);
        }
        return null;
    }
    
    public void registerValidator(final Validator validator) {
        if (validator != null) {
            this.validators.add(validator);
        }
    }
    
    public void deregisterValidator(final Validator validator) {
        this.validators.remove(validator);
    }
    
    public void validate(final boolean validateDescendants) throws ValidationException {
        for (final Validator validator : this.validators) {
            this.log.debug("Validating {} using Validator class {}", (Object)this.getElementQName(), (Object)validator.getClass().getName());
            validator.validate(this);
        }
        if (validateDescendants) {
            this.log.debug("Validating descendants of {}", (Object)this.getElementQName());
            this.validateChildren(this);
        }
    }
    
    protected void validateChildren(final XMLObject xmlObject) throws ValidationException {
        for (final XMLObject childObject : xmlObject.getOrderedChildren()) {
            if (childObject == null) {
                continue;
            }
            if (childObject instanceof ValidatingXMLObject) {
                ((ValidatingXMLObject)childObject).validate(false);
            }
            else {
                this.log.debug("{} does not implement ValidatingXMLObject, ignoring it.", (Object)childObject.getElementQName());
            }
            if (!childObject.hasChildren()) {
                continue;
            }
            this.validateChildren(childObject);
        }
    }
}
