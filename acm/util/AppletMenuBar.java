/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import acm.util.Platform;
import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Hashtable;

public abstract class AppletMenuBar
extends MenuBar
implements ActionListener {
    private Hashtable mnemonics = new Hashtable();
    private int shortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    public AppletMenuBar() {
        this.initMenus();
    }

    public void initMenus() {
        this.add(this.createFileMenu());
    }

    public Menu add(Menu menu) {
        menu.addActionListener(this);
        return super.add(menu);
    }

    public MenuItem createMenuItem(String text) {
        return this.createMenuItem(text, 0, 0);
    }

    public MenuItem createMenuItem(String text, int accelerator) {
        return accelerator > 0 ? new MenuItem(text, new MenuShortcut(accelerator)) : new MenuItem(text);
    }

    public MenuItem createMenuItem(String text, int accelerator, int mnemonic) {
        MenuItem item = this.createMenuItem(text, accelerator);
        this.setMnemonic(item, mnemonic);
        return item;
    }

    public void setMnemonic(MenuItem item, int mnemonic) {
        if (mnemonic > 0) {
            this.mnemonics.put(item, new Integer(mnemonic));
        }
    }

    public void update() {
    }

    public void installInFrame(Component comp) {
        Applet applet = null;
        while (comp != null && !(comp instanceof Frame)) {
            if (comp instanceof Applet) {
                applet = (Applet)comp;
            }
            comp = comp.getParent();
        }
        if (comp == null) {
            return;
        }
        Frame frame = (Frame)comp;
        this.update();
        if (applet != null) {
            try {
                Class programClass = Class.forName("acm.program.Program");
                if (programClass.isInstance(applet)) {
                    Class[] types = new Class[]{Class.forName("java.awt.MenuBar")};
                    Object[] args = new Object[]{this};
                    Method setMenuBar = programClass.getMethod("setMenuBar", types);
                    setMenuBar.invoke(applet, args);
                }
                return;
            }
            catch (Exception programClass) {
                // empty catch block
            }
        }
        frame.setMenuBar(this);
    }

    public void actionPerformed(ActionEvent e) {
        this.menuAction(e.getActionCommand());
    }

    protected Menu createFileMenu() {
        Menu menu = new Menu("File");
        this.addQuitItem(menu);
        return menu;
    }

    protected Menu createEditMenu() {
        Menu menu = new Menu("Edit");
        this.addCutCopyPaste(menu);
        return menu;
    }

    protected void addQuitItem(Menu menu) {
        if (Platform.isMac()) {
            menu.add(this.createMenuItem("Quit", 81));
        } else {
            MenuItem item = this.createMenuItem("Exit", 0, 88);
            item.setActionCommand("Quit");
            menu.add(item);
        }
    }

    public void addCutCopyPaste(Menu menu) {
        if (Platform.isMac()) {
            menu.add(this.createMenuItem("Cut", 88));
            menu.add(this.createMenuItem("Copy", 67));
            menu.add(this.createMenuItem("Paste", 86));
        } else {
            MenuItem item = this.createMenuItem("Cut (x)", 0, 88);
            item.setActionCommand("Cut");
            menu.add(item);
            item = this.createMenuItem("Copy (c)", 0, 67);
            item.setActionCommand("Copy");
            menu.add(item);
            item = this.createMenuItem("Paste (v)", 0, 86);
            item.setActionCommand("Paste");
            menu.add(item);
        }
    }

    public void menuAction(String cmd) {
        if ("Quit".equals(cmd)) {
            System.exit(0);
        }
    }
}

