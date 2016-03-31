/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

class ResizeCanvas
extends Canvas {
    private static final int SIZE = 102;
    private static final Font FONT = new Font("Helvetica", 0, 12);
    private int rows;
    private int cols;
    private int sqSize;

    ResizeCanvas() {
    }

    public void setDimension(int width, int height) {
        this.cols = width;
        this.rows = height;
    }

    public Dimension getPreferredSize() {
        return new Dimension(102, 102);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0, 0, 101, 101);
        int sqSize = 100 / Math.max(this.rows, this.cols);
        int x = (102 - sqSize * (this.cols - 1)) / 2;
        int ix = 1;
        while (ix <= this.cols) {
            int y = (102 + sqSize * (this.rows - 1)) / 2;
            int iy = 1;
            while (iy <= this.rows) {
                this.drawCornerMarker(g, x, y);
                y -= sqSize;
                ++iy;
            }
            x += sqSize;
            ++ix;
        }
        String str = String.valueOf(this.cols) + "x" + this.rows;
        g.setFont(FONT);
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(str) + 6;
        int height = fm.getHeight() + 2;
        g.setColor(Color.white);
        g.fillRect((102 - width) / 2, (102 - height) / 2, width, height);
        g.setColor(Color.black);
        g.drawString(str, (102 - fm.stringWidth(str)) / 2, (102 + fm.getAscent()) / 2);
    }

    private void drawCornerMarker(Graphics g, int x, int y) {
        if (this.sqSize < 11) {
            g.drawLine(x, y, x, y);
        } else {
            g.drawLine(x - 1, y, x + 1, y);
            g.drawLine(x, y - 1, x, y + 1);
        }
    }
}

