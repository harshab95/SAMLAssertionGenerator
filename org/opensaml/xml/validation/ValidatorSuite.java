// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import java.util.List;
import javax.xml.namespace.QName;
import java.util.Map;
import org.slf4j.Logger;

public class ValidatorSuite
{
    private final Logger log;
    private String id;
    private Map<QName, List<Validator>> validators;
    
    public ValidatorSuite(final String suiteId) {
        this.log = LoggerFactory.getLogger((Class)ValidatorSuite.class);
        this.validators = new ConcurrentHashMap<QName, List<Validator>>();
        this.id = suiteId;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void validate(final XMLObject xmlObject) throws ValidationException {
        if (xmlObject == null) {
            return;
        }
        this.log.debug("Beginning to verify XMLObject {} and its children", (Object)xmlObject.getElementQName());
        this.performValidation(xmlObject);
        final List<XMLObject> children = xmlObject.getOrderedChildren();
        if (children != null) {
            for (final XMLObject child : children) {
                this.validate(child);
            }
        }
    }
    
    public List<Validator> getValidators(final QName xmlObjectTarget) {
        return (List<Validator>)Collections.unmodifiableList((List<? extends Validator>)this.validators.get(xmlObjectTarget));
    }
    
    public void registerValidator(final QName xmlObjectTarget, final Validator validator) {
        List<Validator> targetValidators = this.validators.get(xmlObjectTarget);
        if (targetValidators == null) {
            targetValidators = new ArrayList<Validator>();
            this.validators.put(xmlObjectTarget, targetValidators);
        }
        targetValidators.add(validator);
    }
    
    public void deregisterValidator(final QName xmlObjectTarget, final Validator validator) {
        final List<Validator> targetValidators = this.validators.get(xmlObjectTarget);
        if (targetValidators != null) {
            targetValidators.remove(validator);
        }
    }
    
    private void performValidation(final XMLObject xmlObject) throws ValidationException {
        final QName schemaType = xmlObject.getSchemaType();
        if (schemaType != null) {
            this.log.debug("Validating XMLObject {} against validators registered under its schema type {}", (Object)xmlObject.getElementQName(), (Object)schemaType);
            this.performValidation(schemaType, xmlObject);
        }
        this.log.debug("Validating XMLObject {} against validators registered under its element QName", (Object)xmlObject.getElementQName());
        this.performValidation(xmlObject.getElementQName(), xmlObject);
    }
    
    private void performValidation(final QName validatorSetKey, final XMLObject xmlObject) throws ValidationException {
        final List<Validator> elementQNameValidators = this.validators.get(validatorSetKey);
        if (elementQNameValidators != null) {
            for (final Validator validator : elementQNameValidators) {
                this.log.debug("Validating XMLObject {} against Validator {}", (Object)xmlObject.getElementQName(), (Object)validator.getClass().getName());
                validator.validate(xmlObject);
            }
        }
        else {
            this.log.debug("No validators registered for XMLObject {} under QName {}", (Object)xmlObject.getElementQName(), (Object)validatorSetKey);
        }
    }
}
