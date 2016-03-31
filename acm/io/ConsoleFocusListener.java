/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.IOConsole;
import acm.util.AppletMenuBar;
import java.awt.Component;
import java.awt.MenuBar;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class ConsoleFocusListener
implements FocusListener {
    private IOConsole console;

    public ConsoleFocusListener(IOConsole console) {
        this.console = console;
    }

    public void focusGained(FocusEvent e) {
        MenuBar mbar = this.console.getMenuBar();
        if (mbar instanceof AppletMenuBar) {
            ((AppletMenuBar)mbar).installInFrame(this.console);
        }
    }

    public void focusLost(FocusEvent e) {
    }
}

