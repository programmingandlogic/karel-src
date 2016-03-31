/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.util.AppletMenuBar;
import acm.util.Platform;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.Properties;

public class GCanvasMenuBar
extends AppletMenuBar {
    private GCanvas gc;

    public GCanvasMenuBar(GCanvas gc) {
        this.gc = gc;
    }

    public void initMenus() {
        this.add(this.createFileMenu());
    }

    protected Menu createFileMenu() {
        Menu menu = new Menu("File");
        this.setMnemonic(menu, 70);
        menu.add(this.createMenuItem("Print", 80));
        menu.addSeparator();
        this.addQuitItem(menu);
        return menu;
    }

    public void menuAction(String cmd) {
        if (cmd.equals("Print")) {
            Frame frame = Platform.getEnclosingFrame(this.gc);
            if (frame == null) {
                return;
            }
            PrintJob pj = this.gc.getToolkit().getPrintJob(frame, "Graphics", null);
            if (pj == null) {
                return;
            }
            this.gc.print(pj.getGraphics());
            pj.end();
        } else {
            super.menuAction(cmd);
        }
    }
}

