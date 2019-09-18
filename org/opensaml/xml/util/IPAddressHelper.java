// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.slf4j.LoggerFactory;
import java.net.UnknownHostException;
import java.net.InetAddress;
import org.slf4j.Logger;

public final class IPAddressHelper
{
    private IPAddressHelper() {
    }
    
    public static String addressToString(final byte[] address) {
        final Logger log = getLogger();
        if (isIPv4(address)) {
            return ipv4ToString(address);
        }
        if (isIPv6(address)) {
            return ipv6ToString(address);
        }
        log.error("IP address byte array was an invalid length: {}", (Object)address.length);
        return null;
    }
    
    private static String ipv4ToString(final byte[] address) {
        final Logger log = getLogger();
        final StringBuilder builder = new StringBuilder();
        final byte[] ip = new byte[4];
        System.arraycopy(address, 0, ip, 0, 4);
        try {
            builder.append(InetAddress.getByAddress(ip).getHostAddress());
        }
        catch (UnknownHostException e) {
            log.error("Unknown host exception processing IP address byte array: {}", (Object)e.getMessage());
            return null;
        }
        if (hasMask(address)) {
            final byte[] mask = new byte[4];
            System.arraycopy(address, 4, mask, 0, 4);
            builder.append("/");
            try {
                builder.append(InetAddress.getByAddress(mask).getHostAddress());
            }
            catch (UnknownHostException e2) {
                log.error("Unknown host exception processing IP address byte array: {}", (Object)e2.getMessage());
                return null;
            }
        }
        return builder.toString();
    }
    
    private static String ipv6ToString(final byte[] address) {
        final Logger log = getLogger();
        final StringBuilder builder = new StringBuilder();
        final byte[] ip = new byte[16];
        System.arraycopy(address, 0, ip, 0, 16);
        try {
            builder.append(InetAddress.getByAddress(ip).getHostAddress());
        }
        catch (UnknownHostException e) {
            log.error("Unknown host exception processing IP address byte array: {}", (Object)e.getMessage());
            return null;
        }
        if (hasMask(address)) {
            log.error("IPv6 subnet masks are currently unsupported");
            return null;
        }
        return builder.toString();
    }
    
    public static boolean isIPv4(final byte[] address) {
        return address.length == 4 || address.length == 8;
    }
    
    public static boolean isIPv6(final byte[] address) {
        return address.length == 16 || address.length == 32;
    }
    
    public static boolean hasMask(final byte[] address) {
        return address.length == 8 || address.length == 32;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)IPAddressHelper.class);
    }
}
