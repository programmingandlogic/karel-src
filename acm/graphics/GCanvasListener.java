/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.util.AppletMenuBar;
import java.awt.Component;
import java.awt.MenuBar;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class GCanvasListener
implements FocusListener,
MouseListener,
MouseMotionListener,
ComponentListener {
    private GCanvas gc;

    public GCanvasListener(GCanvas gc) {
        this.gc = gc;
    }

    public void mouseClicked(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        this.gc.requestFocus();
        this.gc.dispatchMouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void mouseExited(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void mouseDragged(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void mouseMoved(MouseEvent e) {
        this.gc.dispatchMouseEvent(e);
    }

    public void focusGained(FocusEvent e) {
        MenuBar mbar = this.gc.getMenuBar();
        if (mbar instanceof AppletMenuBar) {
            ((AppletMenuBar)mbar).installInFrame(this.gc);
        }
    }

    public void focusLost(FocusEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        this.gc.initOffscreenImage();
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }
}

