/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.io.IOConsole;
import acm.program.Program;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;

public abstract class ConsoleProgram
extends Program {
    public ConsoleProgram() {
        this.setLayout(new BorderLayout());
        this.add("Center", this.getConsole());
        this.validate();
    }

    public void run() {
    }

    protected IOConsole createConsole() {
        return new IOConsole();
    }
}

