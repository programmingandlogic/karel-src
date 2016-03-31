/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GObject;
import acm.graphics.GPolygon;
import java.awt.Color;

class GPolygonTest
extends GCanvasTest {
    private static final double EDGE = 50.0;

    GPolygonTest() {
    }

    public void test(GCanvas gc) {
        GPolygon p = new GPolygon();
        p.addVertex(-25.0, 25.0 / GPolygonTest.tanD(36.0));
        int theta = 0;
        while (theta < 360) {
            p.addPolarEdge(50.0, theta);
            theta += 72;
        }
        p.setLocation(200.0, 200.0);
        gc.add(p);
        this.waitForClick();
        p.setFilled(true);
        this.waitForClick();
        p.setColor(Color.magenta);
        this.waitForClick();
        int i = 0;
        while (i < 72) {
            p.rotate(8.0);
            this.waitForClick();
            i += 8;
        }
        p.move(50.0, 0.0);
        this.waitForClick();
        p.scale(1.5);
        this.waitForClick();
        p.scale(1.0, -1.0);
        this.waitForClick();
    }

    private static double tanD(double degrees) {
        double radians = degrees * 3.141592653589793 / 180.0;
        return Math.sin(radians) / Math.cos(radians);
    }
}

