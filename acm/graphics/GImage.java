/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GDimension;
import acm.graphics.GImageTest;
import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.GResizable;
import acm.graphics.GScalable;
import acm.util.MediaTools;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class GImage
extends GObject
implements GResizable,
GScalable {
    private Image image;
    private double width;
    private double height;
    private boolean sizeDetermined;

    public GImage(Image image) {
        this(image, 0.0, 0.0);
    }

    public GImage(String name) {
        this(name, 0.0, 0.0);
    }

    public GImage(Image image, double x, double y) {
        this.setImage(image);
        this.setLocation(x, y);
    }

    public GImage(String name, double x, double y) {
        this(MediaTools.loadImage(name), x, y);
    }

    public void setImage(Image image) {
        this.image = MediaTools.loadImage(image);
        this.determineSize();
    }

    public void setImage(String name) {
        this.setImage(MediaTools.loadImage(name));
    }

    public Image getImage() {
        return this.image;
    }

    public void paint(Graphics g) {
        Component imageObserver = this.getComponent();
        if (this.image != null && imageObserver != null) {
            Rectangle r = this.getAWTBounds();
            Color color = this.getObjectColor();
            if (color == null) {
                g.drawImage(this.image, r.x, r.y, r.width, r.height, imageObserver);
            } else {
                g.drawImage(this.image, r.x, r.y, r.width, r.height, color, imageObserver);
            }
        }
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        this.repaint();
    }

    public final void setSize(GDimension size) {
        this.setSize(size.getWidth(), size.getHeight());
    }

    public GDimension getSize() {
        return new GDimension(this.width, this.height);
    }

    public void setBounds(double x, double y, double width, double height) {
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
    }

    public final void setBounds(GRectangle bounds) {
        this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public GRectangle getBounds() {
        this.determineSize();
        return new GRectangle(this.getX(), this.getY(), this.width, this.height);
    }

    public void scale(double sx, double sy) {
        this.width *= sx;
        this.height *= sy;
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    protected Rectangle getAWTBounds() {
        this.determineSize();
        return new Rectangle(GObject.round(this.getX()), GObject.round(this.getY()), GObject.round(this.width), GObject.round(this.height));
    }

    private void determineSize() {
        if (this.sizeDetermined) {
            return;
        }
        Component component = this.getComponent();
        if (component == null) {
            component = MediaTools.getImageObserver();
        }
        this.width = this.image.getWidth(component);
        this.height = this.image.getHeight(component);
        this.sizeDetermined = true;
    }

    public static void test() {
        new GImageTest().main();
    }
}

