/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GArc;
import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;

class GArcTest
extends GCanvasTest {
    GArcTest() {
    }

    public void test(GCanvas gc) {
        gc.add(new GArc(10.0, 10.0, 50.0, 50.0, 0.0, 180.0));
        gc.add(new GArc(70.0, 10.0, 50.0, 100.0, 45.0, 90.0));
        gc.add(new GArc(130.0, 10.0, 50.0, 50.0, 90.0, -270.0));
        this.waitForClick();
    }
}

