/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.AppletMenuBar;
import acm.util.Platform;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.Properties;
import stanford.karel.KarelWorld;

class KarelMenuBar
extends AppletMenuBar {
    private KarelWorld world;

    public KarelMenuBar(KarelWorld world) {
        this.world = world;
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
            Frame frame = Platform.getEnclosingFrame(this.world);
            if (frame == null) {
                return;
            }
            PrintJob pj = this.world.getToolkit().getPrintJob(frame, "Graphics", null);
            if (pj == null) {
                return;
            }
            this.world.print(pj.getGraphics());
            pj.end();
        } else {
            super.menuAction(cmd);
        }
    }
}

