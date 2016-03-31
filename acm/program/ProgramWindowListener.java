/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class ProgramWindowListener
implements WindowListener {
    ProgramWindowListener() {
    }

    public void windowClosing(WindowEvent e) {
        ((Component)e.getSource()).setVisible(false);
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
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

