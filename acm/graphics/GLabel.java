/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GLabelTest;
import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.util.MediaTools;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GLabel
extends GObject {
    public static final Font DEFAULT_FONT = new Font("Default", 0, 12);
    private String str;
    private Font font;
    private static final Component DUMMY_COMPONENT = MediaTools.getImageObserver();

    public GLabel(String str) {
        this(str, 0.0, 0.0);
    }

    public GLabel(String str, double x, double y) {
        this.str = str;
        this.setFont(DEFAULT_FONT);
        this.setLocation(x, y);
    }

    public void setFont(Font font) {
        this.font = font;
        this.repaint();
    }

    public Font getFont() {
        return this.font;
    }

    public void setString(String str) {
        this.str = str;
        this.repaint();
    }

    public String getString() {
        return this.str;
    }

    public void paint(Graphics g) {
        g.setFont(this.font);
        g.drawString(this.str, GObject.round(this.getX()), GObject.round(this.getY()));
    }

    public double getWidth() {
        return this.getFontMetrics().stringWidth(this.str);
    }

    public double getHeight() {
        return this.getFontMetrics().getHeight();
    }

    public double getAscent() {
        return this.getFontMetrics().getAscent();
    }

    public double getDescent() {
        return this.getFontMetrics().getDescent();
    }

    public FontMetrics getFontMetrics() {
        Component comp = this.getComponent();
        if (comp == null) {
            comp = DUMMY_COMPONENT;
        }
        return comp.getFontMetrics(this.font);
    }

    public GRectangle getBounds() {
        return new GRectangle(this.getX(), this.getY() - this.getAscent(), this.getWidth(), this.getHeight());
    }

    public String paramString() {
        return String.valueOf(super.paramString()) + ", string=\"" + this.str + "\"";
    }

    public static void test() {
        new GLabelTest().main();
    }
}

