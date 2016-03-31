/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.AWTDialog;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AWTMessageDialog
extends AWTDialog {
    private Button okButton;

    public AWTMessageDialog(Frame frame, String title, Image icon, String msg) {
        super(frame, title, icon, false);
        this.setMessage(msg);
    }

    public void initButtonPanel(Panel buttonPanel, boolean allowCancel) {
        this.okButton = new Button("OK");
        this.okButton.addActionListener(this);
        buttonPanel.add(this.okButton);
    }

    public void initDataPanel(Panel dataPanel) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.okButton) {
            this.hide();
        }
    }
}

