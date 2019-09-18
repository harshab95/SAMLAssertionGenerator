// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

public final class Version
{
    private static final String NAME;
    private static final String VERSION;
    private static final int MAJOR_VERSION;
    private static final int MINOR_VERSION;
    private static final int MICRO_VERSION;
    
    static {
        final Package pkg = Version.class.getPackage();
        NAME = pkg.getImplementationTitle().intern();
        VERSION = pkg.getImplementationVersion().intern();
        final String[] versionParts = Version.VERSION.split(".");
        MAJOR_VERSION = Integer.parseInt(versionParts[0]);
        MINOR_VERSION = Integer.parseInt(versionParts[1]);
        MICRO_VERSION = Integer.parseInt(versionParts[2]);
    }
    
    private Version() {
    }
    
    public static void main(final String[] args) {
        final Package pkg = Version.class.getPackage();
        System.out.println(String.valueOf(Version.NAME) + " version " + Version.VERSION);
    }
    
    public static String getName() {
        return Version.NAME;
    }
    
    public static String getVersion() {
        return Version.VERSION;
    }
    
    public static int getMajorVersion() {
        return Version.MAJOR_VERSION;
    }
    
    public static int getMinorVersion() {
        return Version.MINOR_VERSION;
    }
    
    public static int getMicroVersion() {
        return Version.MICRO_VERSION;
    }
}
