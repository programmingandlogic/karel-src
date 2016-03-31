/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GFillable;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GPolygonTest;
import acm.graphics.GRectangle;
import acm.graphics.GScalable;
import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Enumeration;
import java.util.Vector;

public class GPolygon
extends GObject
implements GFillable,
GScalable {
    private static final double EPSILON = 1.0E-5;
    private double cx;
    private double cy;
    private double sx;
    private double sy;
    private double rotation;
    private Vector vertexList;
    private boolean cacheValid;
    private boolean complete;
    private Polygon poly;
    private Object lock;
    private boolean fill;
    private Color fillColor;

    public GPolygon() {
        this(0.0, 0.0);
    }

    public GPolygon(double x, double y) {
        this.clear();
        this.lock = new Object();
        this.setLocation(x, y);
    }

    public GPolygon(GPoint[] points) {
        this.clear();
        Object object = this.lock;
        synchronized (object) {
            int i = 0;
            while (i < points.length) {
                this.vertexList.addElement(new GPoint(points[i].getX(), points[i].getY()));
                ++i;
            }
        }
        this.markAsComplete();
        this.rotation = 0.0;
        this.sx = 1.0;
        this.sy = 1.0;
        this.lock = new Object();
        this.setLocation(0.0, 0.0);
    }

    public void addVertex(double x, double y) {
        if (this.complete) {
            throw new ErrorException("You can't add vertices to a GPolygon that has been marked as complete.");
        }
        Object object = this.lock;
        synchronized (object) {
            this.vertexList.addElement(new GPoint(x, y));
            this.cx = x;
            this.cy = y;
        }
    }

    public void addEdge(double dx, double dy) {
        if (this.complete) {
            throw new ErrorException("You can't add edges to a GPolygon that has been marked as complete.");
        }
        Object object = this.lock;
        synchronized (object) {
            this.cx += dx;
            this.cy += dy;
            this.vertexList.addElement(new GPoint(this.cx, this.cy));
        }
    }

    public final void addPolarEdge(double r, double theta) {
        if (this.complete) {
            throw new ErrorException("You can't add edges to a GPolygon that has been marked as complete.");
        }
        Object object = this.lock;
        synchronized (object) {
            this.cx += r * GObject.cosD(theta);
            this.cy -= r * GObject.sinD(theta);
            this.vertexList.addElement(new GPoint(this.cx, this.cy));
        }
    }

    public void scale(double sx, double sy) {
        this.sx *= sx;
        this.sy *= sy;
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    public void rotate(double theta) {
        this.rotation += theta;
        this.repaint();
    }

    public void setFilled(boolean fill) {
        this.fill = fill;
        this.repaint();
    }

    public boolean isFilled() {
        return this.fill;
    }

    public void setFillColor(Color c) {
        this.fillColor = c;
        this.repaint();
    }

    public Color getFillColor() {
        return this.fillColor == null ? this.getColor() : this.fillColor;
    }

    public GRectangle getBounds() {
        int nPoints = this.vertexList.size();
        if (nPoints == 0) {
            return new GRectangle();
        }
        double xMin = 0.0;
        double xMax = 0.0;
        double yMin = 0.0;
        double yMax = 0.0;
        Object object = this.lock;
        synchronized (object) {
            double x0 = this.getX();
            double y0 = this.getY();
            GPoint last = new GPoint(0.0, 0.0);
            double sinTheta = GObject.sinD(this.rotation);
            double cosTheta = GObject.cosD(this.rotation);
            boolean first = true;
            Enumeration e = this.vertexList.elements();
            while (e.hasMoreElements()) {
                GPoint vertex = (GPoint)e.nextElement();
                double x = x0 + this.sx * (cosTheta * vertex.getX() - sinTheta * vertex.getY());
                double y = y0 + this.sy * (sinTheta * vertex.getX() + cosTheta * vertex.getY());
                if (first) {
                    xMin = x;
                    xMax = x;
                    yMin = y;
                    yMax = y;
                    first = false;
                } else {
                    xMin = Math.min(xMin, x);
                    xMax = Math.max(xMax, x);
                    yMin = Math.min(yMin, y);
                    yMax = Math.max(yMax, y);
                }
                last = vertex;
            }
        }
        return new GRectangle(xMin, yMin, xMax - xMin, yMax - yMin);
    }

    public boolean contains(double x, double y) {
        return this.getPolygon().contains(GObject.round(x), GObject.round(y));
    }

    public void paint(Graphics g) {
        if (this.vertexList.size() == 0) {
            return;
        }
        Polygon p = this.getPolygon();
        if (this.isFilled()) {
            g.setColor(this.getFillColor());
            g.fillPolygon(p.xpoints, p.ypoints, p.npoints);
            g.setColor(this.getColor());
        }
        g.drawPolygon(p.xpoints, p.ypoints, p.npoints);
    }

    public void recenter() {
        double xMin = 0.0;
        double xMax = 0.0;
        double yMin = 0.0;
        double yMax = 0.0;
        boolean first = true;
        Enumeration e = this.vertexList.elements();
        while (e.hasMoreElements()) {
            GPoint vertex = (GPoint)e.nextElement();
            if (first) {
                xMin = vertex.getX();
                xMax = vertex.getX();
                yMin = vertex.getY();
                yMax = vertex.getY();
                first = false;
                continue;
            }
            xMin = Math.min(xMin, vertex.getX());
            xMax = Math.max(xMax, vertex.getX());
            yMin = Math.min(yMin, vertex.getY());
            yMax = Math.max(yMax, vertex.getX());
        }
        double xc = (xMin + xMax) / 2.0;
        double yc = (yMin + yMax) / 2.0;
        Enumeration e2 = this.vertexList.elements();
        while (e2.hasMoreElements()) {
            GPoint vertex = (GPoint)e2.nextElement();
            vertex.translate(- xc, - yc);
        }
        this.cacheValid = false;
    }

    public Object clone() {
        try {
            Object clone = super.clone();
            ((GPolygon)clone).copyVertexList();
            return clone;
        }
        catch (Exception CloneNotSupportedException) {
            throw new ErrorException("Impossible exception");
        }
    }

    protected void repaint() {
        this.cacheValid = false;
        super.repaint();
    }

    protected Polygon getPolygon() {
        if (this.cacheValid) {
            return this.poly;
        }
        Object object = this.lock;
        synchronized (object) {
            double x0 = this.getX();
            double y0 = this.getY();
            GPoint last = new GPoint(0.0, 0.0);
            double sinTheta = GObject.sinD(this.rotation);
            double cosTheta = GObject.cosD(this.rotation);
            this.poly = new Polygon();
            Enumeration e = this.vertexList.elements();
            while (e.hasMoreElements()) {
                GPoint vertex = (GPoint)e.nextElement();
                double x = x0 + this.sx * (cosTheta * vertex.getX() - sinTheta * vertex.getY());
                double y = y0 + this.sy * (sinTheta * vertex.getX() + cosTheta * vertex.getY());
                this.poly.addPoint(GObject.round(x), GObject.round(y));
                last = vertex;
            }
            this.cacheValid = true;
            return this.poly;
        }
    }

    protected void markAsComplete() {
        this.complete = true;
    }

    protected void clear() {
        if (this.complete) {
            throw new ErrorException("You can't clear a GPolygon that has been marked as complete.");
        }
        this.vertexList = new Vector();
        this.cx = 0.0;
        this.cy = 0.0;
        this.rotation = 0.0;
        this.sx = 1.0;
        this.sy = 1.0;
        this.cacheValid = false;
    }

    private void copyVertexList() {
        Vector newList = new Vector();
        int i = 0;
        while (i < this.vertexList.size()) {
            newList.addElement(this.vertexList.elementAt(i));
            ++i;
        }
        this.vertexList = newList;
    }

    public static void test() {
        new GPolygonTest().main();
    }
}

