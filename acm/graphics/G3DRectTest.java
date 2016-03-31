/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.G3DRect;
import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;
import java.awt.Color;

class G3DRectTest
extends GCanvasTest {
    G3DRectTest() {
    }

    public void test(GCanvas gc) {
        G3DRect grect = new G3DRect(50.0, 50.0, 75.0, 50.0);
        gc.add(grect);
        grect.setColor(Color.magenta);
        grect.setFilled(true);
        this.waitForClick();
        grect.setRaised(true);
        this.waitForClick();
        grect.setRaised(false);
        this.waitForClick();
        grect = new G3DRect(30.0, 10.0, 100.0, 75.0);
        grect.setColor(Color.green);
        grect.setFilled(true);
        gc.add(grect);
        this.waitForClick();
        grect.setRaised(true);
        this.waitForClick();
        grect.setRaised(false);
        this.waitForClick();
        grect.sendToBack();
        this.waitForClick();
    }
}

