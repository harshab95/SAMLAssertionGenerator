// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import java.util.Collections;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.joda.time.Chronology;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import org.opensaml.xml.schema.XSDateTime;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSDateTimeImpl extends AbstractValidatingXMLObject implements XSDateTime
{
    private DateTime value;
    private DateTimeFormatter formatter;
    
    protected XSDateTimeImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.formatter = ISODateTimeFormat.dateTime().withChronology((Chronology)ISOChronology.getInstanceUTC());
    }
    
    public DateTime getValue() {
        return this.value;
    }
    
    public void setValue(final DateTime newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return Collections.emptyList();
    }
    
    public DateTimeFormatter getDateTimeFormatter() {
        return this.formatter;
    }
    
    public void setDateTimeFormatter(final DateTimeFormatter newFormatter) {
        if (newFormatter == null) {
            throw new IllegalArgumentException("The specified DateTimeFormatter may not be null");
        }
        this.formatter = newFormatter;
    }
}
