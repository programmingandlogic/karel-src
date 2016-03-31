/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;
import acm.graphics.GRect;
import java.awt.Color;

class GRectTest
extends GCanvasTest {
    GRectTest() {
    }

    public void test(GCanvas gc) {
        GRect grect = new GRect(50.0, 50.0, 75.0, 50.0);
        gc.add(grect);
        this.waitForClick();
        grect.setFilled(true);
        this.waitForClick();
        grect.setColor(Color.magenta);
        this.waitForClick();
        grect.move(50.0, 0.0);
        this.waitForClick();
        grect = new GRect(30.0, 10.0, 50.0, 75.0);
        gc.add(grect);
        this.waitForClick();
        grect.setSize(75.0, 75.0);
        this.waitForClick();
    }
}

