/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.PathElement;
import acm.graphics.PathState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

class StartRegionElement
extends PathElement {
    private Color color;

    public StartRegionElement(Color color) {
        this.color = color;
    }

    public void paint(Graphics g, PathState state) {
        state.region = new Polygon();
        state.fillColor = this.color;
    }
}

