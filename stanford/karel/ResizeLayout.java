/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

class ResizeLayout
implements LayoutManager {
    private Component canvas;
    private Component hbar;
    private Component vbar;

    ResizeLayout() {
    }

    public void addLayoutComponent(String constraints, Component comp) {
        if (constraints.equals("canvas")) {
            this.canvas = comp;
        }
        if (constraints.equals("hbar")) {
            this.hbar = comp;
        }
        if (constraints.equals("vbar")) {
            this.vbar = comp;
        }
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        return this.minimumLayoutSize(parent);
    }

    public Dimension minimumLayoutSize(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            Dimension csize = this.canvas.getPreferredSize();
            int hsize = this.hbar.getPreferredSize().height;
            int vsize = this.vbar.getPreferredSize().width;
            return new Dimension(csize.width + vsize + 1, csize.height + hsize + 1);
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
            int hsize = this.hbar.getPreferredSize().height;
            int vsize = this.vbar.getPreferredSize().width;
            this.canvas.setBounds(x, y, width - vsize - 1, height - hsize - 1);
            this.hbar.setBounds(x, y + height - vsize, width - vsize - 1, hsize);
            this.vbar.setBounds(x + width - hsize, y, vsize, height - hsize - 1);
        }
    }
}

