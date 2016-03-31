/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class GProgramListener
implements MouseListener,
MouseMotionListener {
    private GraphicsProgram program;

    public GProgramListener(GraphicsProgram program) {
        this.program = program;
    }

    public void mouseClicked(MouseEvent e) {
        this.wakeup(this.program);
        this.program.onMouseClicked(new GPoint(e.getX(), e.getY()));
    }

    public void mousePressed(MouseEvent e) {
        this.program.onMousePressed(new GPoint(e.getX(), e.getY()));
    }

    public void mouseReleased(MouseEvent e) {
        this.program.onMouseReleased(new GPoint(e.getX(), e.getY()));
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        this.program.onMouseDragged(new GPoint(e.getX(), e.getY()));
    }

    public void mouseMoved(MouseEvent e) {
        this.program.onMouseMoved(new GPoint(e.getX(), e.getY()));
    }

    private void wakeup(Component comp) {
        Component component = comp;
        synchronized (component) {
            comp.notifyAll();
        }
    }
}

