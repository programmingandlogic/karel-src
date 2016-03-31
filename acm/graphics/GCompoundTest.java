/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GRect;
import java.awt.Color;
import java.io.PrintStream;
import java.util.Iterator;

class GCompoundTest
extends GCanvasTest {
    GCompoundTest() {
    }

    public void test(GCanvas gc) {
        GCompound gcomp = new GCompound();
        gcomp.add(new GRect(0.0, 0.0, 75.0, 50.0));
        gcomp.add(new GLine(0.0, 0.0, 75.0, 50.0));
        gcomp.add(new GLine(0.0, 50.0, 75.0, 0.0));
        gcomp.setLocation(50.0, 10.0);
        gc.add(gcomp);
        this.waitForClick();
        gcomp.setColor(Color.blue);
        this.waitForClick();
        gcomp.move(50.0, 0.0);
        this.waitForClick();
        gcomp.setLocation(((double)gc.getWidth() - gcomp.getWidth()) / 2.0, ((double)gc.getHeight() - gcomp.getHeight()) / 2.0);
        this.waitForClick();
        gcomp.scale(2.0);
        this.waitForClick();
        System.out.println("BACK_TO_FRONT");
        Iterator i = gcomp.iterator(0);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
        this.waitForClick();
        System.out.println("FRONT_TO_BACK");
        i = gcomp.iterator(1);
        while (i.hasNext()) {
            Object obj = i.next();
            System.out.println(obj);
            if (!(obj instanceof GLine)) continue;
            i.remove();
        }
        this.waitForClick();
    }
}

