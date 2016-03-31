/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.PathElement;
import acm.graphics.PathState;
import java.awt.Graphics;
import java.awt.Polygon;

class FinalPathElement
extends PathElement {
    public void paint(Graphics g, PathState state) {
        if (state.region != null) {
            g.drawPolyline(state.region.xpoints, state.region.ypoints, state.region.npoints);
        }
    }
}

