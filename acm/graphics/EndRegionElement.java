/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.PathElement;
import acm.graphics.PathState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

class EndRegionElement
extends PathElement {
    public void paint(Graphics g, PathState state) {
        Color oldColor = g.getColor();
        g.setColor(state.fillColor);
        g.fillPolygon(state.region.xpoints, state.region.ypoints, state.region.npoints);
        g.setColor(oldColor);
        g.drawPolygon(state.region.xpoints, state.region.ypoints, state.region.npoints);
        state.region = null;
    }
}

