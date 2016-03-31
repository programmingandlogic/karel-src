/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.program.Program;
import acm.program.ProgramRootPaneLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Panel;

class ProgramRootPane
extends Panel {
    private Program program;
    private Component backgroundPane;
    private Container contentPane;
    private Container glassPane;

    public ProgramRootPane(Program program, Component backgroundPane, Container contentPane, Container glassPane) {
        this.program = program;
        this.backgroundPane = backgroundPane;
        this.contentPane = contentPane;
        this.glassPane = glassPane;
        this.setLayout(new ProgramRootPaneLayout());
        this.add(glassPane);
        this.add(contentPane);
        this.add(backgroundPane);
    }

    public Program getProgram() {
        return this.program;
    }

    public Component getBackgroundPane() {
        return this.backgroundPane;
    }

    public Container getContentPane() {
        return this.contentPane;
    }

    public Container getGlassPane() {
        return this.glassPane;
    }
}

