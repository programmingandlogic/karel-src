/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GDimension;
import acm.graphics.GRectangle;

public interface GResizable {
    public void setSize(double var1, double var3);

    public void setSize(GDimension var1);

    public void setBounds(double var1, double var3, double var5, double var7);

    public void setBounds(GRectangle var1);
}

