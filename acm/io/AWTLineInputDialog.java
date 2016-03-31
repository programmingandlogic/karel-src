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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AWTLineInputDialog
extends AWTDialog {
    private Button cancelButton;
    private Button okButton;
    private TextField textLine;
    private String input;

    public AWTLineInputDialog(Frame frame, String msg, Image icon, boolean allowCancel) {
        super(frame, "Input", icon, allowCancel);
        this.setMessage(msg);
    }

    public String getInput() {
        return this.input;
    }

    public void setVisible(boolean flag) {
        super.setVisible(flag);
        if (flag) {
            this.textLine.requestFocus();
        }
    }

    public void initButtonPanel(Panel buttonPanel, boolean allowCancel) {
        this.okButton = new Button("OK");
        this.okButton.addActionListener(this);
        buttonPanel.add(this.okButton);
        if (allowCancel) {
            this.cancelButton = new Button("Cancel");
            this.cancelButton.addActionListener(this);
            buttonPanel.add(this.cancelButton);
        }
    }

    public void initDataPanel(Panel dataPanel) {
        this.textLine = new TextField();
        this.textLine.addActionListener(this);
        dataPanel.add("South", this.textLine);
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        if (source == this.okButton || source == this.textLine) {
            this.input = this.textLine.getText();
            this.hide();
        } else if (source == this.cancelButton) {
            this.input = null;
            this.hide();
        }
    }
}

