/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GDimension;
import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.graphics.GOvalTest;
import acm.graphics.GRectangle;
import acm.graphics.GResizable;
import acm.graphics.GScalable;
import acm.util.Platform;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GOval
extends GObject
implements GFillable,
GResizable,
GScalable {
    private boolean useArcs;
    private double width;
    private double height;
    private boolean fill;
    private Color fillColor;

    public GOval(double width, double height) {
        this(0.0, 0.0, width, height);
    }

    public GOval(double x, double y, double width, double height) {
        this.useArcs = this.checkForArcRendering();
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
    }

    public boolean contains(double x, double y) {
        double dy;
        double rx = this.width / 2.0;
        double ry = this.height / 2.0;
        if (rx == 0.0 || ry == 0.0) {
            return false;
        }
        double dx = x - (this.getX() + rx);
        if (dx * dx / (rx * rx) + (dy = y - (this.getY() + ry)) * dy / (ry * ry) <= 1.0) {
            return true;
        }
        return false;
    }

    public void paint(Graphics g) {
        Rectangle r = this.getAWTBounds();
        if (this.useArcs) {
            if (this.isFilled()) {
                g.setColor(this.getFillColor());
                g.fillArc(r.x, r.y, r.width, r.height, 0, 360);
                g.setColor(this.getColor());
            }
            g.drawArc(r.x, r.y, r.width, r.height, 0, 360);
        } else {
            if (this.isFilled()) {
                g.setColor(this.getFillColor());
                g.fillOval(r.x, r.y, r.width, r.height);
                g.setColor(this.getColor());
            }
            g.drawOval(r.x, r.y, r.width, r.height);
        }
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

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        this.repaint();
    }

    public final void setSize(GDimension size) {
        this.setSize(size.getWidth(), size.getHeight());
    }

    public GDimension getSize() {
        return new GDimension(this.width, this.height);
    }

    public void setBounds(double x, double y, double width, double height) {
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
    }

    public final void setBounds(GRectangle bounds) {
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public GRectangle getBounds() {
        return new GRectangle(this.getX(), this.getY(), this.width + 1.0, this.height + 1.0);
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void scale(double sx, double sy) {
        this.width *= sx;
        this.height *= sy;
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    protected Rectangle getAWTBounds() {
        return new Rectangle(GObject.round(this.getX()), GObject.round(this.getY()), GObject.round(this.width), GObject.round(this.height));
    }

    private boolean checkForArcRendering() {
        return Platform.isMac();
    }

    public static void test() {
        new GOvalTest().main();
    }
}

