/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.AWTIconCanvas;
import acm.io.AWTMessageCanvas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class AWTDialog
extends Dialog
implements ActionListener {
    public static final int WIDTH = 260;
    public static final int HEIGHT = 100;
    private AWTMessageCanvas messageArea;

    public AWTDialog(Frame frame, String title, Image icon, boolean allowCancel) {
        super(frame, title, true);
        this.setLayout(new BorderLayout());
        Panel topPanel = new Panel();
        Panel buttonPanel = new Panel();
        Panel dataPanel = new Panel();
        topPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        dataPanel.setLayout(new BorderLayout());
        this.messageArea = new AWTMessageCanvas();
        dataPanel.add("Center", this.messageArea);
        this.initButtonPanel(buttonPanel, allowCancel);
        this.initDataPanel(dataPanel);
        topPanel.add("West", new AWTIconCanvas(icon));
        topPanel.add("Center", dataPanel);
        this.add("Center", topPanel);
        this.add("South", buttonPanel);
        Rectangle bounds = frame.getBounds();
        int cx = bounds.x + bounds.width / 2;
        int cy = bounds.y + bounds.height / 2;
        this.setBounds(cx - 130, cy - 50, 260, 100);
        this.validate();
    }

    public abstract void initButtonPanel(Panel var1, boolean var2);

    public abstract void initDataPanel(Panel var1);

    public abstract void actionPerformed(ActionEvent var1);

    public void setMessage(String msg) {
        this.messageArea.setMessage(msg);
    }
}

