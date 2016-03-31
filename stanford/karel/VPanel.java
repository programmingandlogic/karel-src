/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Panel;
import stanford.karel.EmptyCanvas;
import stanford.karel.HVLayout;

class VPanel
extends Panel {
    public VPanel() {
        this.setLayout(new HVLayout(3));
    }

    public Component add(String constraint) {
        return this.add(constraint, null);
    }

    public Component add(Component comp) {
        return this.add("", comp);
    }

    public Component add(String constraint, Component comp) {
        if (comp == null) {
            comp = new EmptyCanvas();
        }
        return super.add(constraint, comp);
    }
}

