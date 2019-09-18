// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.SecurityException;
import java.util.Set;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.Resolver;

public interface PKIXValidationInformationResolver extends Resolver<PKIXValidationInformation, CriteriaSet>
{
    Set<String> resolveTrustedNames(final CriteriaSet p0) throws SecurityException, UnsupportedOperationException;
    
    boolean supportsTrustedNameResolution();
}
