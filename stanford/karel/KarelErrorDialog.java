/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.Platform;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import stanford.karel.HPanel;
import stanford.karel.KarelBugIcon;
import stanford.karel.KarelErrorCanvas;
import stanford.karel.KarelProgram;
import stanford.karel.KarelWorld;
import stanford.karel.VPanel;

class KarelErrorDialog
extends Dialog
implements WindowListener,
ActionListener {
    private static final int DIALOG_WIDTH = 330;
    private static final int DIALOG_HEIGHT = 170;
    private static final int LOGO_WIDTH = 100;
    private static final int LOGO_HEIGHT = 100;
    private static final int BUTTON_WIDTH = 60;
    private static final Font DIALOG_FONT = new Font("Dialog", 0, 12);
    private static final Color DIALOG_BGCOLOR = Color.red;
    private KarelProgram program;
    private Canvas bugIcon;
    private Button okButton;
    private KarelErrorCanvas errorDisplay;

    public KarelErrorDialog(KarelProgram program) {
        super(Platform.getEnclosingFrame(program.getWorld()), true);
        this.setLayout(new BorderLayout());
        this.program = program;
        this.init();
    }

    private void init() {
        this.setSize(330, 170);
        this.setFont(DIALOG_FONT);
        this.setBackground(DIALOG_BGCOLOR);
        this.setResizable(false);
        this.addWindowListener(this);
        this.setLayout(new BorderLayout());
        HPanel hbox = new HPanel();
        VPanel vbox = new VPanel();
        this.bugIcon = new KarelBugIcon();
        this.okButton = new Button("OK");
        this.okButton.addActionListener(this);
        this.errorDisplay = new KarelErrorCanvas();
        this.errorDisplay.setFont(DIALOG_FONT);
        hbox.add("/width:100/height:100", this.bugIcon);
        hbox.add("/stretch:both", this.errorDisplay);
        vbox.add("/stretch:both", hbox);
        vbox.add("/top:3/bottom:3/width:60/center", this.okButton);
        this.add("Center", vbox);
        this.validate();
    }

    public void error(String msg) {
        System.out.println("ERROR");
        this.errorDisplay.setText(msg);
        this.setVisible(true);
    }

    public void windowClosing(WindowEvent e) {
        this.setVisible(false);
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

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        if (source == this.okButton) {
            this.windowClosing(null);
        }
    }
}
