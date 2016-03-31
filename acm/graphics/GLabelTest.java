/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasTest;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import java.awt.Color;
import java.awt.Font;

class GLabelTest
extends GCanvasTest {
    GLabelTest() {
    }

    public void test(GCanvas gc) {
        GLabel glabel = new GLabel("Hello, world.", 50.0, 60.0);
        gc.add(glabel);
        this.waitForClick();
        glabel.setFont(new Font("Serif", 1, 18));
        this.waitForClick();
        glabel.setColor(Color.blue);
        this.waitForClick();
        glabel.move(50.0, 0.0);
        this.waitForClick();
        glabel.setLocation(((double)gc.getWidth() - glabel.getWidth()) / 2.0, 60.0);
        this.waitForClick();
        glabel.setString("Hi!");
        this.waitForClick();
    }
}

