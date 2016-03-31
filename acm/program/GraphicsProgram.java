/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.graphics.GCanvas;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GObjectProgram;
import acm.program.GProgramListener;
import acm.program.Program;
import acm.program.ProgramBorder;
import acm.util.AppletMenuBar;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public abstract class GraphicsProgram
extends Program {
    private static final int BORDER_PIXELS = 4;
    private GCanvas gc;
    private GProgramListener listener;

    protected GraphicsProgram() {
        this.setLayout(new BorderLayout());
        this.listener = new GProgramListener(this);
        this.gc = this.createGCanvas();
        this.gc.addMouseListener(this.listener);
        this.gc.addMouseMotionListener(this.listener);
        this.add("Center", this.gc);
        this.validate();
    }

    public void run() {
    }

    public GCanvas getGCanvas() {
        return this.gc;
    }

    public int getWidth() {
        return this.gc.getWidth();
    }

    public int getHeight() {
        return this.gc.getHeight();
    }

    public void add(GObject gobj) {
        this.gc.add(gobj);
    }

    public final void add(GObject gobj, double x, double y) {
        this.add(gobj);
        gobj.setLocation(x, y);
    }

    public final void add(GObject gobj, GPoint pt) {
        this.add(gobj);
        gobj.setLocation(pt);
    }

    public void remove(GObject gobj) {
        this.gc.remove(gobj);
    }

    public void removeAll() {
        this.gc.removeAll();
    }

    public int getElementCount() {
        return this.gc.getElementCount();
    }

    public GObject getElement(int index) {
        return this.gc.getElement(index);
    }

    public GObject getElementAt(double x, double y) {
        return this.gc.getElementAt(x, y);
    }

    public Iterator iterator() {
        return this.gc.iterator();
    }

    public Iterator iterator(int direction) {
        return this.gc.iterator(direction);
    }

    public void onMouseClicked(GPoint pt) {
    }

    public void onMousePressed(GPoint pt) {
    }

    public void onMouseReleased(GPoint pt) {
    }

    public void onMouseDragged(GPoint pt) {
    }

    public void onMouseMoved(GPoint pt) {
    }

    public void waitForClick() {
        GraphicsProgram graphicsProgram = this;
        synchronized (graphicsProgram) {
            try {
                this.wait();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
    }

    public void repaint() {
        this.gc.repaint();
        super.repaint();
    }

    public void removeAllComponents() {
        super.removeAll();
    }

    public static int round(double x) {
        return GObject.round(x);
    }

    public static double sinD(double angle) {
        return GObject.sinD(angle);
    }

    public static double cosD(double angle) {
        return GObject.cosD(angle);
    }

    public static double tanD(double angle) {
        return GObject.tanD(angle);
    }

    public static double toDegrees(double radians) {
        return GObject.toDegrees(radians);
    }

    public static double toRadians(double degrees) {
        return GObject.toRadians(degrees);
    }

    public static double distance(double x, double y) {
        return GObject.distance(x, y);
    }

    public static double distance(double x0, double y0, double x1, double y1) {
        return GObject.distance(x0, y0, x1, y1);
    }

    public static double angle(double x, double y) {
        return GObject.angle(x, y);
    }

    public static void startGraphicsProgram(GObject gobj, String[] args) {
        GObjectProgram program = new GObjectProgram();
        program.setStartupObject(gobj);
        program.start(args);
    }

    protected Component createBorder(String side) {
        return this.isAppletMode() ? new ProgramBorder(side, 4) : null;
    }

    protected GCanvas createGCanvas() {
        return new GCanvas();
    }

    protected void startHook() {
        MenuBar mbar = this.gc.getMenuBar();
        if (mbar instanceof AppletMenuBar) {
            ((AppletMenuBar)mbar).installInFrame(this.gc);
        }
    }

    protected void endHook() {
        this.gc.repaint();
    }

    protected boolean isStarted() {
        if (this.gc == null || !super.isStarted()) {
            return false;
        }
        Dimension size = this.gc.getSize();
        if (size != null && size.width != 0 && size.height != 0) {
            return true;
        }
        return false;
    }
}

