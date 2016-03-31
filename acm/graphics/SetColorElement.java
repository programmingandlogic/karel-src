/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.PathElement;
import acm.graphics.PathState;
import java.awt.Color;
import java.awt.Graphics;

class SetColorElement
extends PathElement {
    private Color color;

    public SetColorElement(Color color) {
        this.color = color;
    }

    public void paint(Graphics g, PathState state) {
        g.setColor(this.color);
    }
}

