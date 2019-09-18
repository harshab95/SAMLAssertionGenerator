// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import java.util.Collection;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.XMLObject;

public interface KeyInfoProvider
{
    Collection<Credential> process(final KeyInfoCredentialResolver p0, final XMLObject p1, final CriteriaSet p2, final KeyInfoResolutionContext p3) throws SecurityException;
    
    boolean handles(final XMLObject p0);
}
