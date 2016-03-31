/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.io.IODialog;
import acm.io.IOModel;
import acm.program.Program;

public abstract class DialogProgram
extends Program {
    public void run() {
    }

    public IOModel getInputModel() {
        return this.getDialog();
    }

    public IOModel getOutputModel() {
        return this.getDialog();
    }
}

