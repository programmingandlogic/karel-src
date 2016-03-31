/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCompoundTest;
import acm.graphics.GContainer;
import acm.graphics.GMouseEvent;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;
import acm.graphics.GScalable;
import acm.util.ErrorException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class GCompound
extends GObject
implements GContainer,
GScalable {
    private boolean complete = false;
    private Vector contents = new Vector();
    private GObject lastObject;
    private GObject dragObject;

    public void add(GObject gobj) {
        if (this.complete) {
            throw new ErrorException("You can't add objects to a GCompound that has been marked as complete.");
        }
        Vector vector = this.contents;
        synchronized (vector) {
            if (gobj.getParent() != null) {
                gobj.getParent().remove(gobj);
            }
            gobj.setParent(this);
            this.contents.addElement(gobj);
        }
        this.repaint();
    }

    public final void add(GObject gobj, double x, double y) {
        this.add(gobj);
        gobj.setLocation(x, y);
    }

    public final void add(GObject gobj, GPoint pt) {
        this.add(gobj, pt.getX(), pt.getY());
    }

    public void remove(GObject gobj) {
        if (this.complete) {
            throw new ErrorException("You can't remove objects from a GCompound that has been marked as complete.");
        }
        Vector vector = this.contents;
        synchronized (vector) {
            this.contents.removeElement(gobj);
            gobj.setParent(null);
        }
        this.repaint();
    }

    public void removeAll() {
        if (this.complete) {
            throw new ErrorException("You can't remove objects from a GCompound that has been marked as complete.");
        }
        Vector vector = this.contents;
        synchronized (vector) {
            this.contents.removeAllElements();
        }
        this.repaint();
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

    public void dispatchMouseEvent(MouseEvent e) {
        int id;
        GPoint pt = new GPoint((double)e.getX() - this.getX(), (double)e.getY() - this.getY());
        GObject gobj = this.getElementAt(pt);
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

    public void paint(Graphics g) {
        g = g.create();
        g.translate(GObject.round(this.getX()), GObject.round(this.getY()));
        Vector vector = this.contents;
        synchronized (vector) {
            Enumeration e = this.contents.elements();
            while (e.hasMoreElements()) {
                ((GObject)e.nextElement()).paintObject(g);
            }
        }
    }

    public void scale(double sx, double sy) {
        Component comp = this.getComponent();
        boolean oldAutoRepaint = false;
        if (comp instanceof GCanvas) {
            oldAutoRepaint = ((GCanvas)comp).getAutoRepaintFlag();
            ((GCanvas)comp).setAutoRepaintFlag(false);
        }
        int i = this.getElementCount() - 1;
        while (i >= 0) {
            GObject gobj = this.getElement(i);
            gobj.setLocation(sx * gobj.getX(), sy * gobj.getY());
            if (gobj instanceof GScalable) {
                ((GScalable)((Object)gobj)).scale(sx, sy);
            }
            --i;
        }
        if (comp instanceof GCanvas) {
            ((GCanvas)comp).setAutoRepaintFlag(oldAutoRepaint);
        }
        this.repaint();
    }

    public final void scale(double sf) {
        this.scale(sf, sf);
    }

    public GRectangle getBounds() {
        GRectangle bounds = new GRectangle();
        boolean first = true;
        Vector vector = this.contents;
        synchronized (vector) {
            Enumeration e = this.contents.elements();
            while (e.hasMoreElements()) {
                if (first) {
                    bounds = new GRectangle(((GObject)e.nextElement()).getBounds());
                    first = false;
                    continue;
                }
                bounds.add(((GObject)e.nextElement()).getBounds());
            }
        }
        bounds.translate(this.getX(), this.getY());
        return bounds;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean contains(double x, double y) {
        double cx = x - this.getX();
        double cy = y - this.getY();
        Vector vector = this.contents;
        synchronized (vector) {
            GObject gobj;
            Enumeration e = this.contents.elements();
            do {
                if (e.hasMoreElements()) continue;
                return false;
            } while (!(gobj = (GObject)e.nextElement()).contains(cx, cy));
            return true;
        }
    }

    public void markAsComplete() {
        this.complete = true;
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
        this.repaint();
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
        this.repaint();
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
        this.repaint();
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
        this.repaint();
    }

    protected static Iterator createIterator(GContainer container, int direction) {
        try {
            Class iteratorClass = Class.forName("acm.graphics.GIterator");
            Class[] types = new Class[]{Class.forName("acm.graphics.GContainer"), Integer.TYPE};
            Object[] args = new Object[]{container, new Integer(direction)};
            Constructor constructor = iteratorClass.getConstructor(types);
            return (Iterator)constructor.newInstance(args);
        }
        catch (Exception ex) {
            throw new ErrorException("Unable to create an Iterator on this platform.");
        }
    }

    public static void test() {
        new GCompoundTest().main();
    }
}

