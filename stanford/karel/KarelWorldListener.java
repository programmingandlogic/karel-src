/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import stanford.karel.KarelWorld;

class KarelWorldListener
implements MouseListener,
MouseMotionListener,
ComponentListener {
    private KarelWorld world;

    public KarelWorldListener(KarelWorld world) {
        this.world = world;
    }

    public void mousePressed(MouseEvent e) {
        this.world.mousePressedHook(e);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        this.world.mouseDraggedHook(e);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        this.world.componentResizedHook();
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}

