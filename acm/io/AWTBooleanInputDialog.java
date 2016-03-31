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

class AWTBooleanInputDialog
extends AWTDialog {
    private Button trueButton;
    private Button falseButton;
    private Button cancelButton;
    private String trueLabel;
    private String falseLabel;
    private Boolean input;

    public AWTBooleanInputDialog(Frame frame, String msg, Image icon, String trueLabel, String falseLabel, boolean allowCancel) {
        super(frame, "Input", icon, allowCancel);
        this.setMessage(msg);
        this.trueButton.setLabel(trueLabel);
        this.falseButton.setLabel(falseLabel);
    }

    public Boolean getInput() {
        return this.input;
    }

    public void initButtonPanel(Panel buttonPanel, boolean allowCancel) {
        this.trueButton = new Button("True");
        this.trueButton.addActionListener(this);
        buttonPanel.add(this.trueButton);
        this.falseButton = new Button("False");
        this.falseButton.addActionListener(this);
        buttonPanel.add(this.falseButton);
        if (allowCancel) {
            this.cancelButton = new Button("Cancel");
            this.cancelButton.addActionListener(this);
            buttonPanel.add(this.cancelButton);
        }
    }

    public void initDataPanel(Panel dataPanel) {
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        if (source == this.trueButton) {
            this.input = Boolean.TRUE;
            this.hide();
        } else if (source == this.falseButton) {
            this.input = Boolean.FALSE;
            this.hide();
        } else if (source == this.cancelButton) {
            this.input = null;
            this.hide();
        }
    }
}

