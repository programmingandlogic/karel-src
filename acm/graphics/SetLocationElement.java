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

class SetLocationElement
extends PathElement {
    private double x;
    private double y;

    public SetLocationElement(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g, PathState state) {
        state.cx = this.x;
        state.cy = this.y;
        if (state.region != null) {
            state.region.addPoint(GObject.round(state.sx * this.x), GObject.round(state.sy * this.y));
        }
    }

    public void updateBounds(GRectangle bounds, PathState state) {
        state.cx = this.x;
        state.cy = this.y;
    }
}

