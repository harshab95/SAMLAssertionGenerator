// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.joda.time.ReadableInstant;
import org.opensaml.xml.schema.XSDateTime;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.BaseXMLObjectMarshaller;

public class XSDateTimeMarshaller extends BaseXMLObjectMarshaller
{
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final XSDateTime xsDateTime = (XSDateTime)xmlObject;
        XMLHelper.appendTextContent(domElement, xsDateTime.getDateTimeFormatter().print((ReadableInstant)xsDateTime.getValue()));
    }
}
