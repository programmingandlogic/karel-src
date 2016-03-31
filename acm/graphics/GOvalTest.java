/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;
import acm.graphics.GOval;
import java.awt.Color;

class GOvalTest
extends GCanvasTest {
    GOvalTest() {
    }

    public void test(GCanvas gc) {
        GOval goval = new GOval(50.0, 50.0, 75.0, 50.0);
        gc.add(goval);
        this.waitForClick();
        goval.setFilled(true);
        this.waitForClick();
        goval.setColor(Color.magenta);
        this.waitForClick();
        goval.move(50.0, 0.0);
        this.waitForClick();
        goval = new GOval(30.0, 10.0, 50.0, 75.0);
        gc.add(goval);
        this.waitForClick();
        goval.setSize(75.0, 75.0);
        this.waitForClick();
    }
}

