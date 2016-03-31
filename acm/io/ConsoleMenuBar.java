/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.IOConsole;
import acm.util.AppletMenuBar;
import acm.util.ErrorException;
import acm.util.Platform;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class ConsoleMenuBar
extends AppletMenuBar {
    private IOConsole console;

    public ConsoleMenuBar(IOConsole console) {
        this.console = console;
    }

    public void initMenus() {
        this.add(this.createFileMenu());
        this.add(this.createEditMenu());
    }

    protected Menu createFileMenu() {
        Menu menu = new Menu("File");
        this.setMnemonic(menu, 70);
        menu.add(this.createMenuItem("Save", 83));
        menu.add(this.createMenuItem("Save As..."));
        menu.addSeparator();
        menu.add(this.createMenuItem("Print", 80));
        menu.add(this.createMenuItem("Script..."));
        menu.addSeparator();
        this.addQuitItem(menu);
        return menu;
    }

    protected Menu createEditMenu() {
        Menu menu = new Menu("Edit");
        this.setMnemonic(menu, 69);
        this.addCutCopyPaste(menu);
        menu.add(this.createMenuItem("Select All", 65));
        return menu;
    }

    public void menuAction(String cmd) {
        if (cmd.equals("Save")) {
            this.console.save();
        } else if (cmd.equals("Save As...")) {
            this.console.saveAs();
        } else if (cmd.equals("Print")) {
            Frame frame = Platform.getEnclosingFrame(this.console);
            if (frame == null) {
                return;
            }
            PrintJob pj = this.console.getToolkit().getPrintJob(frame, "Console", null);
            if (pj == null) {
                return;
            }
            this.console.print(pj);
            pj.end();
        } else if (cmd.equals("Script...")) {
            this.setInputScript();
        } else if (cmd.equals("Cut")) {
            this.console.cut();
        } else if (cmd.equals("Copy")) {
            this.console.copy();
        } else if (cmd.equals("Paste")) {
            this.console.paste();
        } else if (cmd.equals("Select All")) {
            this.console.selectAll();
        } else {
            super.menuAction(cmd);
        }
    }

    private void setInputScript() {
        Frame frame = Platform.getEnclosingFrame(this.console);
        FileDialog dialog = new FileDialog(frame, "Input Script", 0);
        dialog.setDirectory(System.getProperty("user.dir"));
        dialog.setVisible(true);
        String dirname = dialog.getDirectory();
        String filename = dialog.getFile();
        if (filename != null) {
            try {
                FileReader rd = new FileReader(new File(new File(dirname), filename));
                this.console.setInputScript(new BufferedReader(rd));
            }
            catch (IOException ex) {
                throw new ErrorException(ex);
            }
        }
    }
}

