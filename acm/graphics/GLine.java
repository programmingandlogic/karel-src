/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;
import acm.graphics.GScalable;
import java.awt.Graphics;

public class GLine
extends GObject
implements GScalable {
    public static final double LINE_TOLERANCE = 1.5;
    private double dx;
    private double dy;

    public GLine(double x0, double y0, double x1, double y1) {
        this.setLocation(x0, y0);
        this.dx = x1 - x0;
        this.dy = y1 - y0;
    }

    public void paint(Graphics g) {
        double x = this.getX();
        double y = this.getY();
        g.drawLine(GObject.round(x), GObject.round(y), GObject.round(x + this.dx), GObject.round(y + this.dy));
    }

    public GRectangle getBounds() {
        double x = Math.min(this.getX(), this.getX() + this.dx);
        double y = Math.min(this.getY(), this.getY() + this.dy);
        return new GRectangle(x, y, Math.abs(this.dx) + 1.0, Math.abs(this.dy) + 1.0);
    }

    public void setStartPoint(double x, double y) {
        this.dx += this.getX() - x;
        this.dy += this.getY() - y;
        this.setLocation(x, y);
    }

    public GPoint getStartPoint() {
        return this.getLocation();
    }

    public void setEndPoint(double x, double y) {
        this.dx = x - this.getX();
        this.dy = y - this.getY();
        this.repaint();
    }

    public GPoint getEndPoint() {
        return new GPoint(this.getX() + this.dx, this.getY() + this.dy);
    }

    public void scale(double sx, double sy) {
        this.dx *= sx;
        this.dy *= sy;
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    public boolean contains(double x, double y) {
        double x0 = this.getX();
        double y0 = this.getY();
        double x1 = x0 + this.dx;
        double y1 = y0 + this.dy;
        double tSquared = 2.25;
        if (this.distanceSquared(x, y, x0, y0) < tSquared) {
            return true;
        }
        if (this.distanceSquared(x, y, x1, y1) < tSquared) {
            return true;
        }
        if (x < Math.min(x0, x1) - 1.5) {
            return false;
        }
        if (x > Math.max(x0, x1) + 1.5) {
            return false;
        }
        if (y < Math.min(y0, y1) - 1.5) {
            return false;
        }
        if (y > Math.max(y0, y1) + 1.5) {
            return false;
        }
        if ((float)x0 - (float)x1 == 0.0f && (float)y0 - (float)y1 == 0.0f) {
            return false;
        }
        double u = ((x - x0) * (x1 - x0) + (y - y0) * (y1 - y0)) / this.distanceSquared(x0, y0, x1, y1);
        if (this.distanceSquared(x, y, x0 + u * (x1 - x0), y0 + u * (y1 - y0)) < tSquared) {
            return true;
        }
        return false;
    }

    public String paramString() {
        String tail = super.paramString();
        tail = tail.substring(tail.indexOf(41) + 1);
        GPoint pt = this.getStartPoint();
        String param = "start=(" + pt.getX() + ", " + pt.getY() + ")";
        pt = this.getEndPoint();
        param = String.valueOf(param) + ", end=(" + pt.getX() + ", " + pt.getY() + ")";
        return String.valueOf(param) + tail;
    }

    private double distanceSquared(double x0, double y0, double x1, double y1) {
        return (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
    }
}

