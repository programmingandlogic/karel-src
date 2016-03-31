/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRectTest;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GRoundRect
extends GRect {
    public static final double DEFAULT_ARC = 10.0;
    private double arcWidth;
    private double arcHeight;

    public GRoundRect(double width, double height) {
        this(0.0, 0.0, width, height, 10.0);
    }

    public GRoundRect(double x, double y, double width, double height) {
        this(x, y, width, height, 10.0);
    }

    public GRoundRect(double x, double y, double width, double height, double arcSize) {
        this(x, y, width, height, arcSize, arcSize);
    }

    public GRoundRect(double x, double y, double width, double height, double arcWidth, double arcHeight) {
        super(x, y, width, height);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    public void paint(Graphics g) {
        Rectangle r = this.getAWTBounds();
        int iArcWidth = GObject.round(this.arcWidth);
        int iArcHeight = GObject.round(this.arcHeight);
        if (this.isFilled()) {
            g.setColor(this.getFillColor());
            g.fillRoundRect(r.x, r.y, r.width, r.height, iArcWidth, iArcHeight);
            g.setColor(this.getColor());
        }
        g.drawRoundRect(r.x, r.y, r.width, r.height, iArcWidth, iArcHeight);
    }

    public static void test() {
        new GRoundRectTest().main();
    }
}

