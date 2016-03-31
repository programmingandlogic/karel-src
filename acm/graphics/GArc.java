/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GArcTest;
import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;
import acm.graphics.GScalable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class GArc
extends GObject
implements GFillable,
GScalable {
    public static final double ARC_TOLERANCE = 2.5;
    private double width;
    private double height;
    private double start;
    private double sweep;
    private boolean fill;
    private Color fillColor;

    public GArc(double width, double height, double start, double sweep) {
        this(0.0, 0.0, width, height, start, sweep);
    }

    public GArc(double x, double y, double width, double height, double start, double sweep) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.sweep = sweep;
        this.setLocation(x, y);
    }

    public void setStartAngle(double start) {
        this.start = start;
        this.repaint();
    }

    public double getStartAngle() {
        return this.start;
    }

    public void setSweepAngle(double sweep) {
        this.sweep = sweep;
        this.repaint();
    }

    public double getSweepAngle() {
        return this.sweep;
    }

    public GPoint getStartPoint() {
        return this.getArcPoint(this.start);
    }

    public GPoint getEndPoint() {
        return this.getArcPoint(this.start + this.sweep);
    }

    public void paint(Graphics g) {
        Rectangle r = this.getAWTBounds();
        int cx = GObject.round(this.getX() + this.width / 2.0);
        int cy = GObject.round(this.getY() + this.width / 2.0);
        int iStart = GObject.round(this.start);
        int iSweep = GObject.round(this.sweep);
        if (this.isFilled()) {
            g.setColor(this.getFillColor());
            g.fillArc(r.x, r.y, r.width, r.height, iStart, iSweep);
            g.setColor(this.getColor());
            g.drawArc(r.x, r.y, r.width, r.height, iStart, iSweep);
            Point start = this.getArcPoint(iStart).toPoint();
            g.drawLine(cx, cy, start.x, start.y);
            Point end = this.getArcPoint(iStart + iSweep).toPoint();
            g.drawLine(cx, cy, end.x, end.y);
        } else {
            g.drawArc(r.x, r.y, r.width, r.height, iStart, iSweep);
        }
    }

    public GRectangle getBounds() {
        double rx = this.width / 2.0;
        double ry = this.height / 2.0;
        double cx = this.getX() + rx;
        double cy = this.getY() + ry;
        double p1x = cx + Math.cos(GObject.toRadians(this.start)) * rx;
        double p1y = cy - Math.sin(GObject.toRadians(this.start)) * ry;
        double p2x = cx + Math.cos(GObject.toRadians(this.start + this.sweep)) * rx;
        double p2y = cy - Math.sin(GObject.toRadians(this.start + this.sweep)) * ry;
        double xMin = Math.min(p1x, p2x);
        double xMax = Math.max(p1x, p2x);
        double yMin = Math.min(p1y, p2y);
        double yMax = Math.max(p1y, p2y);
        if (this.containsAngle(0.0)) {
            xMax = cx + rx;
        }
        if (this.containsAngle(90.0)) {
            yMin = cy - ry;
        }
        if (this.containsAngle(180.0)) {
            xMin = cx - rx;
        }
        if (this.containsAngle(270.0)) {
            yMax = cy + ry;
        }
        if (this.isFilled()) {
            xMin = Math.min(xMin, cx);
            yMin = Math.min(yMin, cy);
            xMax = Math.max(xMax, cx);
            yMax = Math.max(yMax, cy);
        }
        return new GRectangle(xMin, yMin, xMax - xMin + 1.0, yMax - yMin + 1.0);
    }

    public boolean contains(double x, double y) {
        double rx = this.width / 2.0;
        double ry = this.height / 2.0;
        if (rx == 0.0 || ry == 0.0) {
            return false;
        }
        double dx = x - (this.getX() + rx);
        double dy = y - (this.getY() + ry);
        double r = dx * dx / (rx * rx) + dy * dy / (ry * ry);
        if (this.isFilled()) {
            if (r > 1.0) {
                return false;
            }
        } else {
            double t = 2.5 / ((rx + ry) / 2.0);
            if (Math.abs(1.0 - r) > t) {
                return false;
            }
        }
        return this.containsAngle(GObject.toDegrees(Math.atan2(- dy, dx)));
    }

    public void setFrameRectangle(double x, double y, double width, double height) {
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
    }

    public final void setFrameRectangle(GRectangle bounds) {
        this.setFrameRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public GRectangle getFrameRectangle() {
        return new GRectangle(this.getX(), this.getY(), this.width, this.height);
    }

    public void scale(double sx, double sy) {
        this.width *= sx;
        this.height *= sy;
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    public void setFilled(boolean fill) {
        this.fill = fill;
        this.repaint();
    }

    public boolean isFilled() {
        return this.fill;
    }

    public void setFillColor(Color c) {
        this.fillColor = c;
        this.repaint();
    }

    public Color getFillColor() {
        return this.fillColor == null ? this.getColor() : this.fillColor;
    }

    protected Rectangle getAWTBounds() {
        return new Rectangle(GObject.round(this.getX()), GObject.round(this.getY()), GObject.round(this.width), GObject.round(this.height));
    }

    public String paramString() {
        String tail = super.paramString();
        tail = tail.substring(tail.indexOf(41) + 1);
        GRectangle r = this.getFrameRectangle();
        String param = "frame=(" + r.getX() + ", " + r.getY() + ", " + r.getWidth() + ", " + r.getHeight() + ")";
        param = String.valueOf(param) + ", start=" + this.start + ", sweep=" + this.sweep;
        return String.valueOf(param) + tail;
    }

    private GPoint getArcPoint(double angle) {
        double rx = this.width / 2.0;
        double ry = this.height / 2.0;
        double cx = this.getX() + rx;
        double cy = this.getY() + ry;
        double theta = GObject.toRadians(angle);
        return new GPoint(cx + rx * Math.cos(theta), cy - ry * Math.sin(theta));
    }

    private boolean containsAngle(double theta) {
        double start = Math.min(this.getStartAngle(), this.getStartAngle() + this.getSweepAngle());
        double sweep = Math.abs(this.getSweepAngle());
        if (sweep >= 360.0) {
            return true;
        }
        double d = start = start < 0.0 ? 360.0 - (- start) % 360.0 : start % 360.0;
        if (start + sweep > 360.0) {
            if (theta < start && theta > start + sweep - 360.0) {
                return false;
            }
            return true;
        }
        if (theta >= start && theta <= start + sweep) {
            return true;
        }
        return false;
    }

    public static void test() {
        new GArcTest().main();
    }
}

