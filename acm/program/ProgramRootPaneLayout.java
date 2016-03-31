/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

class ProgramRootPaneLayout
implements LayoutManager {
    ProgramRootPaneLayout() {
    }

    public void addLayoutComponent(String constraints, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            return parent.getSize();
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            return parent.getSize();
        }
    }

    public void layoutContainer(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            Dimension psize = parent.getSize();
            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = psize.width - insets.left - insets.right;
            int height = psize.height - insets.top - insets.bottom;
            int i = 0;
            while (i < parent.getComponentCount()) {
                parent.getComponent(i).setBounds(x, y, width, height);
                ++i;
            }
        }
    }
}

