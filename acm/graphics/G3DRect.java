/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.G3DRectTest;
import acm.graphics.GRect;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class G3DRect
extends GRect {
    private boolean raised;

    public G3DRect(double width, double height) {
        this(0.0, 0.0, width, height, false);
    }

    public G3DRect(double x, double y, double width, double height) {
        this(x, y, width, height, false);
    }

    public G3DRect(double x, double y, double width, double height, boolean raised) {
        super(x, y, width, height);
        this.raised = raised;
    }

    public void paint(Graphics g) {
        Rectangle r = this.getAWTBounds();
        if (this.isFilled()) {
            g.setColor(this.getFillColor());
            g.fill3DRect(r.x, r.y, r.width, r.height, this.raised);
            g.setColor(this.getColor());
        }
        g.draw3DRect(r.x, r.y, r.width, r.height, this.raised);
    }

    public void setRaised(boolean raised) {
        this.raised = raised;
    }

    public boolean isRaised() {
        return this.raised;
    }

    public static void test() {
        new G3DRectTest().main();
    }
}

