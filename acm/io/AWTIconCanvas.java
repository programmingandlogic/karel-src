/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

class AWTIconCanvas
extends Canvas {
    private Image icon;

    public AWTIconCanvas(Image icon) {
        this.icon = icon;
    }

    public Dimension getMinimumSize() {
        return new Dimension(48, 48);
    }

    public Dimension getPreferredSize() {
        return this.getMinimumSize();
    }

    public void paint(Graphics g) {
        g.drawImage(this.icon, 8, 8, this);
    }
}

