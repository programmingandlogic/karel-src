/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.DialogModel;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

class SwingDialogModel
implements DialogModel {
    private Component owner;

    public SwingDialogModel(Component owner) {
        this.owner = owner;
    }

    public void popupMessage(String msg) {
        JOptionPane.showMessageDialog(this.owner, msg);
    }

    public void popupErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this.owner, msg, "Error", 0);
    }

    public String popupLineInputDialog(String prompt, boolean allowCancel) {
        JOptionPane pane;
        if (allowCancel) {
            pane = new JOptionPane(prompt, 3, 2);
            pane.setWantsInput(true);
        } else {
            Object[] choices = new Object[]{"OK"};
            pane = new JOptionPane(prompt, 3, 2, null, choices, choices[0]);
            pane.setWantsInput(true);
            pane.setInputValue("");
        }
        JDialog dialog = pane.createDialog(this.owner, "Input");
        dialog.setVisible(true);
        Object value = pane.getInputValue();
        if (value == JOptionPane.UNINITIALIZED_VALUE) {
            return null;
        }
        return (String)value;
    }

    public Boolean popupBooleanInputDialog(String prompt, String trueLabel, String falseLabel, boolean allowCancel) {
        Object[] choices;
        if (allowCancel) {
            choices = new Object[3];
            choices[2] = "Cancel";
        } else {
            choices = new Object[]{trueLabel, falseLabel};
        }
        String label = String.valueOf(trueLabel) + "/" + falseLabel + " question";
        int choice = JOptionPane.showOptionDialog(this.owner, prompt, label, -1, 3, null, choices, choices[0]);
        switch (choice) {
            case 0: {
                return Boolean.TRUE;
            }
            case 1: {
                return Boolean.FALSE;
            }
        }
        return null;
    }
}

