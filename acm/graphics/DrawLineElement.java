/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.PathElement;
import acm.graphics.PathState;
import java.awt.Graphics;
import java.awt.Polygon;

class DrawLineElement
extends PathElement {
    private double dx;
    private double dy;

    public DrawLineElement(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void paint(Graphics g, PathState state) {
        int x0 = GObject.round(state.sx * state.cx);
        int y0 = GObject.round(state.sy * state.cy);
        state.cx += this.dx;
        state.cy += this.dy;
        int x1 = GObject.round(state.sx * state.cx);
        int y1 = GObject.round(state.sy * state.cy);
        if (state.region == null) {
            g.drawLine(x0, y0, x1, y1);
        } else {
            state.region.addPoint(x1, y1);
        }
    }

    public void updateBounds(GRectangle bounds, PathState state) {
        if (bounds.getWidth() < 0.0) {
            bounds.setBounds(state.sx * state.cx, state.sy * state.cy, 0.0, 0.0);
        } else {
            bounds.add(state.sx * state.cx, state.sy * state.cy);
        }
        state.cx += this.dx;
        state.cy += this.dy;
        bounds.add(state.sx * state.cx, state.sy * state.cy);
    }
}

