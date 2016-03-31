/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.DrawLineElement;
import acm.graphics.EndRegionElement;
import acm.graphics.FinalPathElement;
import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.PathElement;
import acm.graphics.PathState;
import acm.graphics.PenImage;
import acm.graphics.SetColorElement;
import acm.graphics.SetLocationElement;
import acm.graphics.StartRegionElement;
import acm.util.ErrorException;
import acm.util.MediaTools;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Vector;

public class GPen
extends GObject {
    private static final double SLOW_DELAY = 200.0;
    private static final double FAST_DELAY = 0.0;
    private static PathElement finalElement = new FinalPathElement();
    private double sx;
    private double sy;
    private double speed = 1.0;
    private boolean regionOpen;
    private boolean regionStarted;
    private boolean penVisible = false;
    private Vector path = new Vector();
    private Image penImage;
    private Color fillColor;

    public GPen() {
        this.erasePath();
    }

    public GPen(double x, double y) {
        this();
        this.setLocation(x, y);
    }

    public void erasePath() {
        this.path = new Vector();
        this.sx = 1.0;
        this.sy = 1.0;
        this.regionOpen = false;
        this.regionStarted = false;
        this.repaint();
    }

    public void setLocation(double x, double y) {
        if (this.regionStarted) {
            throw new ErrorException("It is illegal to move the pen while you are defining a filled region.");
        }
        super.setLocation(x, y);
        this.delay();
    }

    public void drawLine(double dx, double dy) {
        double x = this.getX();
        double y = this.getY();
        Vector vector = this.path;
        synchronized (vector) {
            if (!this.regionStarted) {
                this.path.addElement(new SetLocationElement(x, y));
                this.regionStarted = this.regionOpen;
            }
            this.path.addElement(new DrawLineElement(dx, dy));
        }
        super.setLocation(x + dx, y + dy);
        this.delay();
    }

    public final void drawPolarLine(double r, double theta) {
        double radians = theta * 3.141592653589793 / 180.0;
        this.drawLine(r * Math.cos(radians), (- r) * Math.sin(radians));
    }

    public void setColor(Color color) {
        if (this.regionStarted) {
            throw new ErrorException("It is illegal to change the color while you are defining a filled region.");
        }
        Vector vector = this.path;
        synchronized (vector) {
            this.path.addElement(new SetColorElement(color));
        }
        super.setColor(color);
    }

    public void setFillColor(Color color) {
        if (this.regionStarted) {
            throw new ErrorException("It is illegal to change the fill color while you are defining a filled region.");
        }
        this.fillColor = color;
    }

    public Color getFillColor() {
        return this.fillColor == null ? this.getColor() : this.fillColor;
    }

    public void startFilledRegion() {
        if (this.regionOpen) {
            throw new ErrorException("You are already filling a region.");
        }
        this.regionOpen = true;
        this.regionStarted = false;
        Vector vector = this.path;
        synchronized (vector) {
            this.path.addElement(new StartRegionElement(this.fillColor));
        }
    }

    public void endFilledRegion() {
        if (!this.regionOpen) {
            throw new ErrorException("You need to call startFilledRegion before you call endFilledRegion.");
        }
        this.regionOpen = false;
        this.regionStarted = false;
        Vector vector = this.path;
        synchronized (vector) {
            this.path.addElement(new EndRegionElement());
        }
        this.repaint();
    }

    public void setPenVisible(boolean visible) {
        this.penVisible = visible;
        this.repaint();
        this.delay();
    }

    public boolean isPenVisible() {
        return this.penVisible;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void scale(double sx, double sy) {
        this.sx = sx;
        this.sy = sy;
        this.repaint();
    }

    public void paint(Graphics g) {
        PathState state = new PathState();
        state.sx = this.sx;
        state.sy = this.sy;
        Vector vector = this.path;
        synchronized (vector) {
            Enumeration e = this.path.elements();
            while (e.hasMoreElements()) {
                PathElement element = (PathElement)e.nextElement();
                element.paint(g, state);
            }
        }
        finalElement.paint(g, state);
        if (this.penVisible) {
            this.drawPen(g);
        }
    }

    public GRectangle getBounds() {
        PathState state = new PathState();
        GRectangle bounds = new GRectangle(-1.0, -1.0, -1.0, -1.0);
        state.sx = this.sx;
        state.sy = this.sy;
        Vector vector = this.path;
        synchronized (vector) {
            Enumeration e = this.path.elements();
            while (e.hasMoreElements()) {
                PathElement element = (PathElement)e.nextElement();
                element.updateBounds(bounds, state);
            }
        }
        return bounds;
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public void setPenImage(Image image) {
        this.penImage = MediaTools.loadImage(image);
    }

    public Image getPenImage() {
        if (this.penImage == null) {
            this.penImage = PenImage.getImage();
        }
        return this.penImage;
    }

    protected void drawPen(Graphics g) {
        Component comp = this.getComponent();
        if (comp == null) {
            return;
        }
        if (this.penImage == null) {
            this.penImage = PenImage.getImage();
        }
        int width = this.penImage.getWidth(comp);
        int height = this.penImage.getHeight(comp);
        int x = (int)Math.round(this.getX());
        int y = (int)Math.round(this.getY());
        g.drawImage(this.penImage, x - width / 2, y - height / 2, comp);
    }

    protected Rectangle getPenBounds() {
        Component comp = this.getComponent();
        if (comp == null) {
            return new Rectangle();
        }
        if (this.penImage == null) {
            this.penImage = PenImage.getImage();
        }
        int width = this.penImage.getWidth(comp);
        int height = this.penImage.getHeight(comp);
        int x = (int)Math.round(this.getX());
        int y = (int)Math.round(this.getY());
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    private Rectangle getAWTRectangle(double x, double y, double dx, double dy) {
        return new Rectangle((int)Math.min(x, x + dx), (int)Math.min(y, y + dy), (int)Math.abs(dx), (int)Math.abs(dy));
    }

    private void delay() {
        double delay = 200.0 + Math.sqrt(this.speed) * -200.0;
        if (this.speed < 0.97) {
            GObject.pause(delay);
        }
    }
}

