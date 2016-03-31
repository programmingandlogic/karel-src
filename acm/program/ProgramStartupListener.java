/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

class ProgramStartupListener
implements ComponentListener {
    ProgramStartupListener() {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        this.componentShown(e);
    }

    public void componentShown(ComponentEvent e) {
        ProgramStartupListener programStartupListener = this;
        synchronized (programStartupListener) {
            this.notifyAll();
        }
    }
}

