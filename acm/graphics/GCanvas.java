/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvasListener;
import acm.graphics.GCanvasMenuBar;
import acm.graphics.GCanvasTest;
import acm.graphics.GCompound;
import acm.graphics.GContainer;
import acm.graphics.GMouseEvent;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class GCanvas
extends Container
implements GContainer {
    private GCanvasListener gCanvasListener;
    private GObject lastObject;
    private GObject dragObject;
    private Vector contents = new Vector();
    private MenuBar menuBar;
    private Image offscreenImage;
    private boolean autoRepaint;
    private boolean nativeArcFlag;
    private boolean menuBarInitialized;
    private boolean opaque;

    public GCanvas() {
        this.setBackground(Color.white);
        this.setOpaque(true);
        this.setAutoRepaintFlag(true);
        this.setLayout(null);
        this.gCanvasListener = new GCanvasListener(this);
        this.addFocusListener(this.gCanvasListener);
        this.addComponentListener(this.gCanvasListener);
        this.addMouseListener(this.gCanvasListener);
        this.addMouseMotionListener(this.gCanvasListener);
    }

    public void add(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            if (gobj.getParent() != null) {
                gobj.getParent().remove(gobj);
            }
            gobj.setParent(this);
            this.contents.addElement(gobj);
        }
        this.conditionalRepaint();
    }

    public final void add(GObject gobj, double x, double y) {
        this.add(gobj);
        gobj.setLocation(x, y);
    }

    public final void add(GObject gobj, GPoint pt) {
        this.add(gobj, pt.getX(), pt.getY());
    }

    public void remove(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            this.contents.removeElement(gobj);
            gobj.setParent(null);
        }
        this.conditionalRepaint();
    }

    public void removeAll() {
        Vector vector = this.contents;
        synchronized (vector) {
            this.contents.removeAllElements();
        }
        super.removeAll();
        this.repaint();
    }

    public Component add(Component comp) {
        super.add(comp);
        Dimension size = comp.getSize();
        if (size.width == 0 || size.height == 0) {
            Dimension preferredSize = comp.getPreferredSize();
            if (size.width == 0) {
                size.width = preferredSize.width;
            }
            if (size.height == 0) {
                size.height = preferredSize.height;
            }
            comp.setSize(size);
        }
        return comp;
    }

    public final void add(Component comp, double x, double y) {
        comp.setLocation(GObject.round(x), GObject.round(y));
        this.add(comp);
    }

    public final void add(Component comp, GPoint pt) {
        this.add(comp, pt.getX(), pt.getY());
    }

    public void remove(Component comp) {
        Vector vector = this.contents;
        synchronized (vector) {
            super.remove(comp);
        }
        this.conditionalRepaint();
    }

    public int getElementCount() {
        return this.contents.size();
    }

    public GObject getElement(int index) {
        return (GObject)this.contents.elementAt(index);
    }

    public GObject getElementAt(double x, double y) {
        Vector vector = this.contents;
        synchronized (vector) {
            int i = this.getElementCount() - 1;
            while (i >= 0) {
                GObject gobj = this.getElement(i);
                if (gobj.contains(x, y)) {
                    return gobj;
                }
                --i;
            }
        }
        return null;
    }

    public final GObject getElementAt(GPoint pt) {
        return this.getElementAt(pt.getX(), pt.getY());
    }

    public Iterator iterator() {
        return this.iterator(0);
    }

    public Iterator iterator(int direction) {
        return GCompound.createIterator(this, direction);
    }

    public MenuBar getMenuBar() {
        if (!this.menuBarInitialized) {
            this.menuBar = this.createMenuBar();
            this.menuBarInitialized = true;
        }
        return this.menuBar;
    }

    public void setOpaque(boolean flag) {
        this.opaque = flag;
        this.conditionalRepaint();
    }

    public boolean isOpaque() {
        return this.opaque;
    }

    public int getWidth() {
        return this.getSize().width;
    }

    public int getHeight() {
        return this.getSize().height;
    }

    public void paint(Graphics g) {
        Graphics g0 = g;
        if (this.isOpaque()) {
            if (this.offscreenImage == null) {
                this.initOffscreenImage();
            }
            if (this.offscreenImage != null) {
                g = this.offscreenImage.getGraphics();
            }
            Dimension size = this.getSize();
            g.setColor(this.getBackground());
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(this.getForeground());
        }
        Vector size = this.contents;
        synchronized (size) {
            Enumeration e = this.contents.elements();
            while (e.hasMoreElements()) {
                ((GObject)e.nextElement()).paintObject(g);
            }
        }
        if (this.isOpaque() && this.offscreenImage != null) {
            g0.drawImage(this.offscreenImage, 0, 0, this);
        }
        super.paint(g0);
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void setAutoRepaintFlag(boolean state) {
        this.autoRepaint = state;
    }

    public boolean getAutoRepaintFlag() {
        return this.autoRepaint;
    }

    public void setNativeArcFlag(boolean state) {
        this.nativeArcFlag = state;
    }

    public boolean getNativeArcFlag() {
        return this.nativeArcFlag;
    }

    protected MenuBar createMenuBar() {
        return new GCanvasMenuBar(this);
    }

    protected void sendToFront(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            int index = this.contents.indexOf(gobj);
            if (index >= 0) {
                this.contents.removeElementAt(index);
                this.contents.addElement(gobj);
            }
        }
        this.conditionalRepaint();
    }

    protected void sendToBack(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            int index = this.contents.indexOf(gobj);
            if (index >= 0) {
                this.contents.removeElementAt(index);
                this.contents.insertElementAt(gobj, 0);
            }
        }
        this.conditionalRepaint();
    }

    protected void sendForward(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            int index = this.contents.indexOf(gobj);
            if (index >= 0) {
                this.contents.removeElementAt(index);
                this.contents.insertElementAt(gobj, Math.min(this.contents.size(), index + 1));
            }
        }
        this.conditionalRepaint();
    }

    protected void sendBackward(GObject gobj) {
        Vector vector = this.contents;
        synchronized (vector) {
            int index = this.contents.indexOf(gobj);
            if (index >= 0) {
                this.contents.removeElementAt(index);
                this.contents.insertElementAt(gobj, Math.max(0, index - 1));
            }
        }
        this.conditionalRepaint();
    }

    protected void dispatchMouseEvent(MouseEvent e) {
        int id;
        GObject gobj = this.getElementAt(e.getX(), e.getY());
        GMouseEvent newEvent = null;
        if (gobj != this.lastObject) {
            if (this.lastObject != null) {
                newEvent = new GMouseEvent(this.lastObject, 505, e);
                this.lastObject.fireMouseListeners(newEvent);
            }
            if (gobj != null) {
                newEvent = new GMouseEvent(gobj, 504, e);
                gobj.fireMouseListeners(newEvent);
            }
        }
        this.lastObject = gobj;
        if (this.dragObject != null) {
            gobj = this.dragObject;
        }
        if (gobj != null && (id = e.getID()) != 505 && id != 504) {
            if (id == 501) {
                this.dragObject = gobj;
            } else if (id == 502) {
                this.dragObject = null;
            }
            newEvent = new GMouseEvent(gobj, id, e);
            gobj.fireMouseListeners(newEvent);
        }
        if (newEvent != null && newEvent.isConsumed()) {
            e.consume();
        }
    }

    protected void initOffscreenImage() {
        Vector vector = this.contents;
        synchronized (vector) {
            Dimension size = this.getSize();
            this.offscreenImage = this.createImage(size.width, size.height);
        }
    }

    protected void conditionalRepaint() {
        if (this.autoRepaint) {
            this.repaint();
        }
    }

    public static void test() {
        new GCanvasTest().main();
    }
}

