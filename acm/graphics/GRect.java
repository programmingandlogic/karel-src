/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GDimension;
import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.graphics.GRectTest;
import acm.graphics.GRectangle;
import acm.graphics.GResizable;
import acm.graphics.GScalable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GRect
extends GObject
implements GFillable,
GResizable,
GScalable {
    private double width;
    private double height;
    private boolean fill;
    private Color fillColor;

    public GRect(double width, double height) {
        this(0.0, 0.0, width, height);
    }

    public GRect(double x, double y, double width, double height) {
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
    }

    public void paint(Graphics g) {
        Rectangle r = this.getAWTBounds();
        if (this.isFilled()) {
            g.setColor(this.getFillColor());
            g.fillRect(r.x, r.y, r.width, r.height);
            g.setColor(this.getColor());
        }
        g.drawRect(r.x, r.y, r.width, r.height);
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

    public static void test() {
        new GRectTest().main();
    }
}

