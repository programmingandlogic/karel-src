/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GFillable;
import acm.graphics.GObject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class GCanvasTest
implements MouseListener,
WindowListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 350;

    GCanvasTest() {
    }

    public void main() {
        String name = this.getClass().getName();
        Frame frame = new Frame(name.substring(name.lastIndexOf(46) + 1));
        GCanvas gc = new GCanvas();
        frame.setLayout(new BorderLayout());
        frame.add("Center", gc);
        frame.setBounds(50, 50, 500, 350);
        frame.addWindowListener(this);
        frame.show();
        gc.addMouseListener(this);
        do {
            gc.removeAll();
            this.test(gc);
        } while (true);
    }

    public void test(GCanvas gc) {
    }

    public void waitForClick() {
        GCanvasTest gCanvasTest = this;
        synchronized (gCanvasTest) {
            try {
                this.wait();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        GCanvas gc = (GCanvas)e.getSource();
        GObject obj = gc.getElementAt(e.getX(), e.getY());
        if (obj == null) {
            GCanvasTest gCanvasTest = this;
            synchronized (gCanvasTest) {
                this.notifyAll();
            }
        } else if (e.isControlDown()) {
            if (e.isShiftDown()) {
                obj.sendToBack();
            } else {
                obj.sendToFront();
            }
        } else if (e.isShiftDown()) {
            if (obj instanceof GFillable) {
                ((GFillable)((Object)obj)).setFilled(!((GFillable)((Object)obj)).isFilled());
            }
        } else {
            int rgb = obj.getColor().getRGB() & 16777215;
            obj.setColor(new Color(rgb ^ 16777215));
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}

