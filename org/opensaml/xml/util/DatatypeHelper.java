// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public final class DatatypeHelper
{
    private DatatypeHelper() {
    }
    
    public static boolean isEmpty(final String s) {
        if (s != null) {
            final String sTrimmed = s.trim();
            if (sTrimmed.length() > 0) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean safeEquals(final T s1, final T s2) {
        if (s1 == null || s2 == null) {
            return s1 == s2;
        }
        return s1.equals(s2);
    }
    
    public static String safeTrim(final String s) {
        if (s != null) {
            return s.trim();
        }
        return null;
    }
    
    public static String safeTrimOrNullString(final String s) {
        if (s != null) {
            final String sTrimmed = s.trim();
            if (sTrimmed.length() > 0) {
                return sTrimmed;
            }
        }
        return null;
    }
    
    public static byte[] intToByteArray(final int integer) {
        final byte[] intBytes = { (byte)((integer & 0xFF000000) >>> 24), (byte)((integer & 0xFF0000) >>> 16), (byte)((integer & 0xFF00) >>> 8), (byte)(integer & 0xFF) };
        return intBytes;
    }
    
    public static byte[] fileToByteArray(final File file) throws IOException {
        final long numOfBytes = file.length();
        if (numOfBytes > 2147483647L) {
            throw new IOException("File is to large to be read in to a byte array");
        }
        final byte[] bytes = new byte[(int)numOfBytes];
        final FileInputStream ins = new FileInputStream(file);
        int offset = 0;
        int numRead = 0;
        do {
            numRead = ins.read(bytes, offset, bytes.length - offset);
            offset += numRead;
        } while (offset < bytes.length && numRead >= 0);
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        ins.close();
        return bytes;
    }
    
    public static String inputstreamToString(final InputStream input, final CharsetDecoder decoder) throws IOException {
        CharsetDecoder charsetDecoder = decoder;
        if (decoder == null) {
            charsetDecoder = Charset.defaultCharset().newDecoder();
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(input, charsetDecoder));
        final StringBuilder stringBuffer = new StringBuilder();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            stringBuffer.append(line).append("\n");
        }
        reader.close();
        return stringBuffer.toString();
    }
    
    public static List<String> stringToList(final String string, final String delimiter) {
        if (delimiter == null) {
            throw new IllegalArgumentException("String delimiter may not be null");
        }
        final ArrayList<String> values = new ArrayList<String>();
        final String trimmedString = safeTrimOrNullString(string);
        if (trimmedString != null) {
            final StringTokenizer tokens = new StringTokenizer(trimmedString, delimiter);
            while (tokens.hasMoreTokens()) {
                values.add(tokens.nextToken());
            }
        }
        return values;
    }
    
    public static String listToStringValue(final List<String> values, final String delimiter) {
        if (delimiter == null) {
            throw new IllegalArgumentException("String delimiter may not be null");
        }
        final StringBuilder stringValue = new StringBuilder();
        final Iterator<String> valueItr = values.iterator();
        while (valueItr.hasNext()) {
            stringValue.append(valueItr.next());
            if (valueItr.hasNext()) {
                stringValue.append(delimiter);
            }
        }
        return stringValue.toString();
    }
}
