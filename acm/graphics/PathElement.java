/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GRectangle;
import acm.graphics.PathState;
import java.awt.Graphics;

abstract class PathElement {
    public void paint(Graphics g, PathState state) {
    }

    public void updateBounds(GRectangle bounds, PathState state) {
    }
}

