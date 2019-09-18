// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.Criteria;

public final class PeerEntityIDCriteria implements Criteria
{
    private String peerID;
    
    public PeerEntityIDCriteria(final String peer) {
        this.setPeerID(peer);
    }
    
    public String getPeerID() {
        return this.peerID;
    }
    
    public void setPeerID(final String peer) {
        final String trimmed = DatatypeHelper.safeTrimOrNullString(peer);
        if (trimmed == null) {
            throw new IllegalArgumentException("Peer entity ID criteria must be supplied");
        }
        this.peerID = trimmed;
    }
}
