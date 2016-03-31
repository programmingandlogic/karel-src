/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.util.ErrorException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class ProgramBorder
extends Canvas {
    private static final Color OUTER_BORDER = Color.black;
    private static final Color OUTER_RING = Color.white;
    private static final Color INNER_RING = new Color(12303291);
    private static final Color INNER_BORDER = Color.black;
    private int width;
    private int border;

    public ProgramBorder(String side, int width) {
        if (width < 0 || width > 4) {
            throw new ErrorException("Standard border widths must be between 0 and 4.");
        }
        this.width = width;
        if (side.equalsIgnoreCase("North")) {
            this.border = 11;
        } else if (side.equalsIgnoreCase("South")) {
            this.border = 15;
        } else if (side.equalsIgnoreCase("East")) {
            this.border = 13;
        } else if (side.equalsIgnoreCase("West")) {
            this.border = 17;
        } else {
            throw new ErrorException("Illegal border specification - " + side);
        }
    }

    public void paint(Graphics g) {
        if (this.width == 0) {
            return;
        }
        Dimension size = this.getSize();
        int width = size.width;
        int height = size.height;
        switch (this.border) {
            case 11: {
                this.paintNorthBorder(g, width, height);
                break;
            }
            case 15: {
                this.paintSouthBorder(g, width, height);
                break;
            }
            case 13: {
                this.paintEastBorder(g, width, height);
                break;
            }
            case 17: {
                this.paintWestBorder(g, width, height);
            }
        }
    }

    private void paintNorthBorder(Graphics g, int width, int height) {
        g.setColor(OUTER_BORDER);
        g.drawLine(0, 0, width - 1, 0);
        g.drawLine(0, 0, 0, height - 1);
        g.drawLine(width - 1, 0, width - 1, height - 1);
        switch (height) {
            case 2: {
                g.setColor(INNER_BORDER);
                g.drawLine(1, 1, width - 2, 1);
                break;
            }
            case 3: {
                g.setColor(INNER_RING);
                g.drawLine(1, 1, width - 2, 1);
                g.drawLine(1, 1, 1, 2);
                g.drawLine(width - 2, 1, width - 2, 2);
                g.setColor(INNER_BORDER);
                g.drawLine(2, 2, width - 3, 2);
                break;
            }
            case 4: {
                g.setColor(OUTER_RING);
                g.drawLine(1, 1, width - 2, 1);
                g.drawLine(1, 1, 1, 3);
                g.drawLine(width - 2, 1, width - 2, 3);
                g.setColor(INNER_RING);
                g.drawLine(2, 2, width - 3, 2);
                g.drawLine(2, 2, 2, 3);
                g.drawLine(width - 3, 2, width - 3, 3);
                g.setColor(INNER_BORDER);
                g.drawLine(3, 3, width - 4, 3);
            }
        }
    }

    private void paintSouthBorder(Graphics g, int width, int height) {
        g.setColor(OUTER_BORDER);
        g.drawLine(0, height - 1, width - 1, height - 1);
        g.drawLine(0, 0, 0, height - 1);
        g.drawLine(width - 1, 0, width - 1, height - 1);
        switch (height) {
            case 2: {
                g.setColor(INNER_BORDER);
                g.drawLine(1, 0, width - 2, 0);
                break;
            }
            case 3: {
                g.setColor(INNER_RING);
                g.drawLine(1, 1, width - 2, 1);
                g.drawLine(1, 0, 1, 1);
                g.drawLine(width - 2, 0, width - 2, 1);
                g.setColor(INNER_BORDER);
                g.drawLine(2, 0, width - 3, 0);
                break;
            }
            case 4: {
                g.setColor(OUTER_RING);
                g.drawLine(1, 2, width - 2, 2);
                g.drawLine(1, 2, 1, 0);
                g.drawLine(width - 2, 2, width - 2, 0);
                g.setColor(INNER_RING);
                g.drawLine(2, 1, width - 3, 1);
                g.drawLine(2, 1, 2, 0);
                g.drawLine(width - 3, 1, width - 3, 0);
                g.setColor(INNER_BORDER);
                g.drawLine(3, 0, width - 4, 0);
            }
        }
    }

    private void paintWestBorder(Graphics g, int width, int height) {
        g.setColor(OUTER_BORDER);
        g.drawLine(0, 0, 0, height - 1);
        switch (width) {
            case 2: {
                g.setColor(INNER_BORDER);
                g.drawLine(1, 0, 1, height - 1);
                break;
            }
            case 3: {
                g.setColor(INNER_RING);
                g.drawLine(1, 0, 1, height - 1);
                g.setColor(INNER_BORDER);
                g.drawLine(2, 0, 2, height - 1);
                break;
            }
            case 4: {
                g.setColor(OUTER_RING);
                g.drawLine(1, 0, 1, height - 1);
                g.setColor(INNER_RING);
                g.drawLine(2, 0, 2, height - 1);
                g.setColor(INNER_BORDER);
                g.drawLine(3, 0, 3, height - 1);
            }
        }
    }

    private void paintEastBorder(Graphics g, int width, int height) {
        g.setColor(OUTER_BORDER);
        g.drawLine(width - 1, 0, width - 1, height - 1);
        switch (width) {
            case 2: {
                g.setColor(INNER_BORDER);
                g.drawLine(0, 0, 0, height - 1);
                break;
            }
            case 3: {
                g.setColor(INNER_RING);
                g.drawLine(1, 0, 1, height - 1);
                g.setColor(INNER_BORDER);
                g.drawLine(0, 0, 0, height - 1);
                break;
            }
            case 4: {
                g.setColor(OUTER_RING);
                g.drawLine(2, 0, 2, height - 1);
                g.setColor(INNER_RING);
                g.drawLine(1, 0, 1, height - 1);
                g.setColor(INNER_BORDER);
                g.drawLine(0, 0, 0, height - 1);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.width);
    }
}

