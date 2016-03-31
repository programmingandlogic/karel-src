/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GObject;
import acm.graphics.GPoint;

public interface GContainer {
    public static final int BACK_TO_FRONT = 0;
    public static final int FRONT_TO_BACK = 1;

    public void add(GObject var1);

    public void add(GObject var1, double var2, double var4);

    public void add(GObject var1, GPoint var2);

    public void remove(GObject var1);

    public void removeAll();

    public int getElementCount();

    public GObject getElement(int var1);

    public GObject getElementAt(double var1, double var3);

    public GObject getElementAt(GPoint var1);
}

