/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GImage;
import acm.graphics.GObject;

class GImageTest
extends GCanvasTest {
    GImageTest() {
    }

    public void test(GCanvas gc) {
        GImage gimage = new GImage("frog.gif", 50.0, 50.0);
        gc.add(gimage);
        this.waitForClick();
        gimage.setLocation(0.0, 0.0);
        this.waitForClick();
        gimage.setSize(2.0 * gimage.getWidth(), 2.0 * gimage.getHeight());
        this.waitForClick();
    }
}

