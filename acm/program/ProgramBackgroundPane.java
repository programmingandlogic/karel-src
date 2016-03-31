/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class ProgramBackgroundPane
extends Canvas {
    ProgramBackgroundPane() {
    }

    public void paint(Graphics g) {
        Dimension size = this.getSize();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, size.width, size.height);
    }
}

