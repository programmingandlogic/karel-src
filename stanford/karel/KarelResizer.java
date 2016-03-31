/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import stanford.karel.ResizeCanvas;
import stanford.karel.ResizeLayout;

class KarelResizer
extends Panel
implements AdjustmentListener {
    private static final int MAX_WIDTH = 50;
    private static final int MAX_HEIGHT = 50;
    private ResizeCanvas resizeCanvas = new ResizeCanvas();
    private Scrollbar widthScrollbar;
    private Scrollbar heightScrollbar;

    public KarelResizer() {
        this.resizeCanvas.setDimension(10, 10);
        this.widthScrollbar = new Scrollbar(0);
        this.widthScrollbar.setValues(9, 1, 0, 50);
        this.widthScrollbar.addAdjustmentListener(this);
        this.heightScrollbar = new Scrollbar(1);
        this.heightScrollbar.setValues(40, 1, 0, 50);
        this.heightScrollbar.addAdjustmentListener(this);
        this.setLayout(new ResizeLayout());
        this.add("canvas", this.resizeCanvas);
        this.add("hbar", this.widthScrollbar);
        this.add("vbar", this.heightScrollbar);
    }

    public int getColumns() {
        return this.widthScrollbar.getValue() + 1;
    }

    public int getRows() {
        return 50 - this.heightScrollbar.getValue();
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        this.resizeCanvas.setDimension(this.getColumns(), this.getRows());
        this.resizeCanvas.repaint();
    }
}

