/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GContainer;
import acm.graphics.GDimension;
import acm.graphics.GFillable;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;
import acm.graphics.GResizable;
import acm.util.ErrorException;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Method;

public abstract class GObject
implements Cloneable {
    private GContainer parent;
    private Color color;
    private boolean visible = true;
    private double x;
    private double y;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;

    protected GObject() {
    }

    public abstract void paint(Graphics var1);

    public abstract GRectangle getBounds();

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        this.repaint();
    }

    public final void setLocation(GPoint pt) {
        this.setLocation(pt.getX(), pt.getY());
    }

    public GPoint getLocation() {
        return new GPoint(this.x, this.y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void move(double dx, double dy) {
        this.setLocation(this.x + dx, this.y + dy);
    }

    public final void movePolar(double r, double theta) {
        double radians = theta * 3.141592653589793 / 180.0;
        this.move(r * Math.cos(radians), (- r) * Math.sin(radians));
    }

    public GDimension getSize() {
        GRectangle bounds = this.getBounds();
        return new GDimension(bounds.getWidth(), bounds.getHeight());
    }

    public double getWidth() {
        return this.getBounds().getWidth();
    }

    public double getHeight() {
        return this.getBounds().getHeight();
    }

    public boolean contains(double x, double y) {
        return this.getBounds().contains(GObject.round(x), GObject.round(y));
    }

    public final boolean contains(GPoint pt) {
        return this.contains(pt.getX(), pt.getY());
    }

    public void sendToFront() {
        if (this.parent == null) {
            return;
        }
        if (this.parent instanceof GCanvas) {
            ((GCanvas)this.parent).sendToFront(this);
        } else if (this.parent instanceof GCompound) {
            ((GCompound)this.parent).sendToFront(this);
        } else {
            try {
                Class<?> parentClass = this.parent.getClass();
                Class[] types = new Class[]{Class.forName("acm.graphics.GObject")};
                Object[] args = new Object[]{this};
                Method fn = parentClass.getMethod("sendToFront", types);
                if (fn != null) {
                    fn.invoke(this.parent, args);
                }
            }
            catch (Exception parentClass) {
                // empty catch block
            }
        }
    }

    public void sendToBack() {
        if (this.parent == null) {
            return;
        }
        if (this.parent instanceof GCanvas) {
            ((GCanvas)this.parent).sendToBack(this);
        } else if (this.parent instanceof GCompound) {
            ((GCompound)this.parent).sendToBack(this);
        } else {
            try {
                Class<?> parentClass = this.parent.getClass();
                Class[] types = new Class[]{Class.forName("acm.graphics.GObject")};
                Object[] args = new Object[]{this};
                Method fn = parentClass.getMethod("sendToBack", types);
                if (fn != null) {
                    fn.invoke(this.parent, args);
                }
            }
            catch (Exception parentClass) {
                // empty catch block
            }
        }
    }

    public void sendForward() {
        if (this.parent == null) {
            return;
        }
        if (this.parent instanceof GCanvas) {
            ((GCanvas)this.parent).sendForward(this);
        } else if (this.parent instanceof GCompound) {
            ((GCompound)this.parent).sendForward(this);
        } else {
            try {
                Class<?> parentClass = this.parent.getClass();
                Class[] types = new Class[]{Class.forName("acm.graphics.GObject")};
                Object[] args = new Object[]{this};
                Method fn = parentClass.getMethod("sendForward", types);
                if (fn != null) {
                    fn.invoke(this.parent, args);
                }
            }
            catch (Exception parentClass) {
                // empty catch block
            }
        }
    }

    public void sendBackward() {
        if (this.parent == null) {
            return;
        }
        if (this.parent instanceof GCanvas) {
            ((GCanvas)this.parent).sendBackward(this);
        } else if (this.parent instanceof GCompound) {
            ((GCompound)this.parent).sendBackward(this);
        } else {
            try {
                Class<?> parentClass = this.parent.getClass();
                Class[] types = new Class[]{Class.forName("acm.graphics.GObject")};
                Object[] args = new Object[]{this};
                Method fn = parentClass.getMethod("sendBackward", types);
                if (fn != null) {
                    fn.invoke(this.parent, args);
                }
            }
            catch (Exception parentClass) {
                // empty catch block
            }
        }
    }

    public void setColor(Color c) {
        this.color = c;
        this.repaint();
    }

    public Color getColor() {
        GObject obj = this;
        while (obj.color == null) {
            GContainer parent = obj.getParent();
            if (parent instanceof Component) {
                return ((Component)((Object)parent)).getForeground();
            }
            obj = (GObject)((Object)parent);
        }
        return obj.color;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        this.repaint();
    }

    public boolean isVisible() {
        return this.visible;
    }

    public String toString() {
        String name = this.getClass().getName();
        if (name.startsWith("acm.graphics.")) {
            name = name.substring("acm.graphics.".length());
        }
        return String.valueOf(name) + "[" + this.paramString() + "]";
    }

    public GContainer getParent() {
        return this.parent;
    }

    public static void main(String[] args) {
        try {
            Class<?> programClass = Class.forName("acm.program.Program");
            String[] newArgs = new String[args.length + 1];
            int i = 0;
            while (i < args.length) {
                newArgs[i] = args[i];
                ++i;
            }
            newArgs[args.length] = "program=acm.program.GObjectProgram";
            Class[] types = new Class[]{newArgs.getClass()};
            Object[] params = new Object[]{newArgs};
            Method main = programClass.getMethod("main", types);
            main.invoke(null, params);
        }
        catch (Exception ex) {
            throw new ErrorException(ex);
        }
    }

    public static void pause(double milliseconds) {
        try {
            int millis = (int)milliseconds;
            int nanos = (int)Math.round((milliseconds - (double)millis) * 1000000.0);
            Thread.sleep(millis, nanos);
        }
        catch (InterruptedException millis) {
            // empty catch block
        }
    }

    public static int round(double x) {
        return (int)Math.round(x);
    }

    public static double sinD(double angle) {
        return Math.sin(GObject.toRadians(angle));
    }

    public static double cosD(double angle) {
        return Math.cos(GObject.toRadians(angle));
    }

    public static double tanD(double angle) {
        return GObject.sinD(angle) / GObject.cosD(angle);
    }

    public static double toDegrees(double radians) {
        return radians * 180.0 / 3.141592653589793;
    }

    public static double toRadians(double degrees) {
        return degrees * 3.141592653589793 / 180.0;
    }

    public static double distance(double x, double y) {
        return GObject.distance(0.0, 0.0, x, y);
    }

    public static double distance(double x0, double y0, double x1, double y1) {
        return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
    }

    public static double angle(double x, double y) {
        if (x == 0.0 && y == 0.0) {
            return 0.0;
        }
        return GObject.toDegrees(Math.atan2(- y, x));
    }

    public void addMouseListener(MouseListener listener) {
        this.mouseListener = AWTEventMulticaster.add(this.mouseListener, listener);
    }

    public void removeMouseListener(MouseListener listener) {
        this.mouseListener = AWTEventMulticaster.remove(this.mouseListener, listener);
    }

    public void addMouseMotionListener(MouseMotionListener listener) {
        this.mouseMotionListener = AWTEventMulticaster.add(this.mouseMotionListener, listener);
    }

    public void removeMouseMotionListener(MouseMotionListener listener) {
        this.mouseMotionListener = AWTEventMulticaster.remove(this.mouseMotionListener, listener);
    }

    protected void start() {
        this.start(null);
    }

    protected void start(String[] args) {
        try {
            Class<?> programClass = Class.forName("acm.program.GraphicsProgram");
            Class<?> gObjectClass = Class.forName("acm.graphics.GObject");
            Class[] types = new Class[]{gObjectClass, args.getClass()};
            Object[] params = new Object[]{this, args};
            Method startGraphicsProgram = programClass.getMethod("startGraphicsProgram", types);
            startGraphicsProgram.invoke(null, params);
        }
        catch (Exception ex) {
            throw new ErrorException(ex);
        }
    }

    protected void fireMouseListeners(MouseEvent e) {
        switch (e.getID()) {
            case 501: {
                if (this.mouseListener == null) break;
                this.mouseListener.mousePressed(e);
                break;
            }
            case 502: {
                if (this.mouseListener == null) break;
                this.mouseListener.mouseReleased(e);
                break;
            }
            case 500: {
                if (this.mouseListener == null) break;
                this.mouseListener.mouseClicked(e);
                break;
            }
            case 505: {
                if (this.mouseListener == null) break;
                this.mouseListener.mouseExited(e);
                break;
            }
            case 504: {
                if (this.mouseListener == null) break;
                this.mouseListener.mouseEntered(e);
                break;
            }
            case 503: {
                if (this.mouseMotionListener == null) break;
                this.mouseMotionListener.mouseMoved(e);
                break;
            }
            case 506: {
                if (this.mouseMotionListener == null) break;
                this.mouseMotionListener.mouseDragged(e);
            }
        }
    }

    protected Color getObjectColor() {
        return this.color;
    }

    protected String paramString() {
        String param = "";
        if (this instanceof GResizable) {
            GRectangle r = this.getBounds();
            param = String.valueOf(param) + "bounds=(" + r.getX() + ", " + r.getY() + ", " + r.getWidth() + ", " + r.getHeight() + ")";
        } else {
            GPoint pt = this.getLocation();
            param = String.valueOf(param) + "location=(" + pt.getX() + ", " + pt.getY() + ")";
        }
        if (this.color != null) {
            param = String.valueOf(param) + ", color=" + GObject.colorName(this.color);
        }
        if (this instanceof GFillable) {
            param = String.valueOf(param) + ", filled=" + ((GFillable)((Object)this)).isFilled();
            Color fillColor = ((GFillable)((Object)this)).getFillColor();
            if (fillColor != null && fillColor != this.color) {
                param = String.valueOf(param) + ", fillColor=" + GObject.colorName(fillColor);
            }
        }
        return param;
    }

    protected static String colorName(Color color) {
        if (color == Color.black) {
            return "black";
        }
        if (color == Color.blue) {
            return "blue";
        }
        if (color == Color.cyan) {
            return "cyan";
        }
        if (color == Color.darkGray) {
            return "darkGray";
        }
        if (color == Color.gray) {
            return "gray";
        }
        if (color == Color.green) {
            return "green";
        }
        if (color == Color.lightGray) {
            return "lightGray";
        }
        if (color == Color.magenta) {
            return "magenta";
        }
        if (color == Color.orange) {
            return "orange";
        }
        if (color == Color.pink) {
            return "pink";
        }
        if (color == Color.red) {
            return "red";
        }
        if (color == Color.white) {
            return "white";
        }
        if (color == Color.yellow) {
            return "yellow";
        }
        return "0x" + Integer.toString(color.getRGB(), 16).toUpperCase();
    }

    protected void paintObject(Graphics g) {
        if (!this.isVisible()) {
            return;
        }
        Color oldColor = g.getColor();
        if (this.color != null) {
            g.setColor(this.color);
        }
        this.paint(g);
        if (this.color != null) {
            g.setColor(oldColor);
        }
    }

    protected void setParent(GContainer parent) {
        this.parent = parent;
    }

    protected Component getComponent() {
        GContainer parent = this.getParent();
        while (parent instanceof GObject) {
            parent = ((GObject)((Object)parent)).getParent();
        }
        return (Component)((Object)parent);
    }

    protected void repaint() {
        GContainer parent = this.getParent();
        while (parent instanceof GObject) {
            parent = ((GObject)((Object)parent)).getParent();
        }
        if (parent instanceof GCanvas) {
            ((GCanvas)parent).conditionalRepaint();
        }
    }
}

