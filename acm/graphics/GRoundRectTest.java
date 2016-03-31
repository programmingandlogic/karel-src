/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;
import acm.graphics.GRoundRect;
import java.awt.Color;

class GRoundRectTest
extends GCanvasTest {
    GRoundRectTest() {
    }

    public void test(GCanvas gc) {
        GRoundRect grect = new GRoundRect(50.0, 50.0, 75.0, 50.0);
        gc.add(grect);
        this.waitForClick();
        grect.setFilled(true);
        this.waitForClick();
        grect.setColor(Color.magenta);
        this.waitForClick();
        grect.move(50.0, 0.0);
        this.waitForClick();
        grect = new GRoundRect(30.0, 10.0, 50.0, 75.0);
        gc.add(grect);
        this.waitForClick();
        grect.setSize(75.0, 75.0);
        this.waitForClick();
    }
}

