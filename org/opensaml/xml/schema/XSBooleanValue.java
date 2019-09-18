// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import org.opensaml.xml.util.DatatypeHelper;

public class XSBooleanValue
{
    private boolean numeric;
    private Boolean value;
    
    public XSBooleanValue() {
        this.numeric = false;
        this.value = null;
    }
    
    public XSBooleanValue(final Boolean newValue, final boolean numericRepresentation) {
        this.numeric = numericRepresentation;
        this.value = newValue;
    }
    
    public Boolean getValue() {
        return this.value;
    }
    
    public void setValue(final Boolean newValue) {
        this.value = newValue;
    }
    
    public boolean isNumericRepresentation() {
        return this.numeric;
    }
    
    public void setNumericRepresentation(final boolean numericRepresentation) {
        this.numeric = numericRepresentation;
    }
    
    @Override
    public int hashCode() {
        int hash;
        if (this.numeric) {
            if (this.value == null) {
                hash = 0;
            }
            else if (this.value) {
                hash = 1;
            }
            else {
                hash = 3;
            }
        }
        else if (this.value == null) {
            hash = 4;
        }
        else if (this.value) {
            hash = 5;
        }
        else {
            hash = 6;
        }
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj instanceof XSBooleanValue && this.hashCode() == obj.hashCode());
    }
    
    @Override
    public String toString() {
        return toString(this.value, this.numeric);
    }
    
    public static String toString(final Boolean value, final boolean numericRepresentation) {
        if (value == null) {
            return "false";
        }
        if (!numericRepresentation) {
            return value.toString();
        }
        if (value) {
            return "1";
        }
        return "0";
    }
    
    public static XSBooleanValue valueOf(final String booleanString) {
        final String trimmedBooleanString = DatatypeHelper.safeTrimOrNullString(booleanString);
        if (trimmedBooleanString != null) {
            if (trimmedBooleanString.equals("1")) {
                return new XSBooleanValue(Boolean.TRUE, true);
            }
            if (trimmedBooleanString.equals("0")) {
                return new XSBooleanValue(Boolean.FALSE, true);
            }
            if (trimmedBooleanString.equals("true")) {
                return new XSBooleanValue(Boolean.TRUE, false);
            }
        }
        return new XSBooleanValue(Boolean.FALSE, false);
    }
}
