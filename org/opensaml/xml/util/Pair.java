// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

public class Pair<T1, T2>
{
    private T1 first;
    private T2 second;
    
    public Pair(final T1 newFirst, final T2 newSecond) {
        this.first = newFirst;
        this.second = newSecond;
    }
    
    public T1 getFirst() {
        return this.first;
    }
    
    public void setFirst(final T1 newFirst) {
        this.first = newFirst;
    }
    
    public T2 getSecond() {
        return this.second;
    }
    
    public void setSecond(final T2 newSecond) {
        this.second = newSecond;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Pair) {
            final Pair<T1, T2> otherPair = (Pair<T1, T2>)o;
            return DatatypeHelper.safeEquals(this.getFirst(), otherPair.getFirst()) && DatatypeHelper.safeEquals(this.getSecond(), otherPair.getSecond());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        if (this.first != null) {
            result = 37 * result + this.first.hashCode();
        }
        if (this.second != null) {
            result = 37 * result + this.second.hashCode();
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "(" + this.getFirst() + "," + this.getSecond() + ")";
    }
}
