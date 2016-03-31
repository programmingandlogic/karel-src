/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

public class ErrorException
extends RuntimeException {
    public ErrorException(String msg) {
        super(msg);
    }

    public ErrorException(Exception ex) {
        super(String.valueOf(ex.getClass().getName()) + ": " + ex.getMessage());
    }
}

