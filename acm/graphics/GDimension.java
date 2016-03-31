/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import java.awt.Dimension;

public class GDimension {
    private double width;
    private double height;

    public GDimension() {
        this(0.0, 0.0);
    }

    public GDimension(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public GDimension(GDimension size) {
        this(size.width, size.height);
    }

    public GDimension(Dimension size) {
        this(size.width, size.height);
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(GDimension size) {
        this.setSize(size.width, size.height);
    }

    public GDimension getSize() {
        return new GDimension(this.width, this.height);
    }

    public Dimension toDimension() {
        return new Dimension((int)Math.round(this.width), (int)Math.round(this.height));
    }

    public int hashCode() {
        return new Float((float)this.width).hashCode() ^ new Float((float)this.height).hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GDimension)) {
            return false;
        }
        GDimension dim = (GDimension)obj;
        if ((float)this.width == (float)dim.width && (float)this.height == (float)dim.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "(" + (float)this.width + "x" + (float)this.height + ")";
    }
}

