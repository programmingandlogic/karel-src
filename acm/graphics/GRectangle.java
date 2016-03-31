/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GDimension;
import acm.graphics.GPoint;
import java.awt.Rectangle;

public class GRectangle {
    private double x;
    private double y;
    private double width;
    private double height;

    public GRectangle() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public GRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GRectangle(double width, double height) {
        this(0.0, 0.0, width, height);
    }

    public GRectangle(GPoint pt, GDimension size) {
        this(pt.getX(), pt.getY(), size.getWidth(), size.getHeight());
    }

    public GRectangle(GPoint pt) {
        this(pt.getX(), pt.getY(), 0.0, 0.0);
    }

    public GRectangle(GDimension size) {
        this(0.0, 0.0, size.getWidth(), size.getHeight());
    }

    public GRectangle(GRectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setBounds(GPoint pt, GDimension size) {
        this.setBounds(pt.getX(), pt.getY(), size.getWidth(), size.getHeight());
    }

    public void setBounds(GRectangle bounds) {
        this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public GRectangle getBounds() {
        return new GRectangle(this);
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(GPoint pt) {
        this.setLocation(pt.getX(), pt.getY());
    }

    public GPoint getLocation() {
        return new GPoint(this.x, this.y);
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(GDimension size) {
        this.setSize(size.getWidth(), size.getHeight());
    }

    public GDimension getSize() {
        return new GDimension(this.width, this.height);
    }

    public void grow(double dx, double dy) {
        this.x -= dx;
        this.y -= dy;
        this.width += 2.0 * dx;
        this.height += 2.0 * dy;
    }

    public boolean isEmpty() {
        if (this.width > 0.0 && this.height > 0.0) {
            return false;
        }
        return true;
    }

    public boolean contains(double x, double y) {
        if (x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height) {
            return true;
        }
        return false;
    }

    public boolean contains(GPoint pt) {
        return this.contains(pt.getX(), pt.getY());
    }

    public boolean intersects(GRectangle r) {
        if (this.x > r.x + r.width) {
            return false;
        }
        if (this.y > r.y + r.height) {
            return false;
        }
        if (r.x > this.x + this.width) {
            return false;
        }
        if (r.y > this.y + this.height) {
            return false;
        }
        return true;
    }

    public GRectangle intersection(GRectangle r) {
        double x1 = Math.max(this.x, r.x);
        double y1 = Math.max(this.y, r.y);
        double x2 = Math.min(this.x + this.width, r.x + r.width);
        double y2 = Math.min(this.y + this.height, r.y + r.height);
        return new GRectangle(x1, y1, x2 - x1, y2 - y1);
    }

    public GRectangle union(GRectangle r) {
        if (this.isEmpty()) {
            return new GRectangle(r);
        }
        if (r.isEmpty()) {
            return new GRectangle(this);
        }
        double x1 = Math.min(this.x, r.x);
        double y1 = Math.min(this.y, r.y);
        double x2 = Math.max(this.x + this.width, r.x + r.width);
        double y2 = Math.max(this.y + this.height, r.y + r.height);
        return new GRectangle(x1, y1, x2 - x1, y2 - y1);
    }

    public void add(GRectangle r) {
        if (r.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            this.setBounds(r);
            return;
        }
        double x2 = Math.max(this.x + this.width, r.x + r.width);
        double y2 = Math.max(this.y + this.height, r.y + r.height);
        this.x = Math.min(r.x, this.x);
        this.y = Math.min(r.y, this.y);
        this.width = x2 - this.x;
        this.height = y2 - this.y;
    }

    public void add(double x, double y) {
        if (this.isEmpty()) {
            this.setBounds(x, y, 0.0, 0.0);
            return;
        }
        double x2 = Math.max(x + this.width, x);
        double y2 = Math.max(y + this.height, y);
        this.x = Math.min(x, this.x);
        this.y = Math.min(y, this.y);
        this.width = x2 - this.x;
        this.height = y2 - this.y;
    }

    public Rectangle toRectangle() {
        return new Rectangle((int)Math.round(this.x), (int)Math.round(this.y), (int)Math.round(this.width), (int)Math.round(this.height));
    }

    public int hashCode() {
        return new Float((float)this.x).hashCode() ^ new Float((float)this.y).hashCode() ^ new Float((float)this.width).hashCode() ^ new Float((float)this.height).hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GRectangle)) {
            return false;
        }
        GRectangle r = (GRectangle)obj;
        if ((float)this.x != (float)r.x) {
            return false;
        }
        if ((float)this.y != (float)r.y) {
            return false;
        }
        if ((float)this.width != (float)r.width) {
            return false;
        }
        if ((float)this.height != (float)r.height) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "[" + (float)this.x + ", " + (float)this.y + ", " + (float)this.width + "x" + (float)this.height + "]";
    }
}

