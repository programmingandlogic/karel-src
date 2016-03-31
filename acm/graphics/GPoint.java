/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import java.awt.Point;

public class GPoint {
    private double x;
    private double y;

    public GPoint() {
        this(0.0, 0.0);
    }

    public GPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GPoint(GPoint p) {
        this(p.x, p.y);
    }

    public GPoint(Point p) {
        this(p.x, p.y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(GPoint p) {
        this.setLocation(p.x, p.y);
    }

    public GPoint getLocation() {
        return new GPoint(this.x, this.y);
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public Point toPoint() {
        return new Point((int)Math.round(this.x), (int)Math.round(this.y));
    }

    public int hashCode() {
        return new Float((float)this.x).hashCode() ^ new Float((float)this.y).hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GPoint)) {
            return false;
        }
        GPoint pt = (GPoint)obj;
        if ((float)this.x == (float)pt.x && (float)this.y == (float)pt.y) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "(" + (float)this.x + ", " + (float)this.y + ")";
    }
}

