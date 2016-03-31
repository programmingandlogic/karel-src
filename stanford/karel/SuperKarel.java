/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Color;
import java.awt.Point;
import stanford.karel.Karel;
import stanford.karel.KarelWorld;

public class SuperKarel
extends Karel {
    public static final Color BLACK = Color.black;
    public static final Color BLUE = Color.blue;
    public static final Color CYAN = Color.cyan;
    public static final Color DARK_GRAY = Color.darkGray;
    public static final Color GRAY = Color.gray;
    public static final Color GREEN = Color.green;
    public static final Color LIGHT_GRAY = Color.lightGray;
    public static final Color MAGENTA = Color.magenta;
    public static final Color ORANGE = Color.orange;
    public static final Color PINK = Color.pink;
    public static final Color RED = Color.red;
    public static final Color WHITE = Color.white;
    public static final Color YELLOW = Color.yellow;

    public void run() {
    }

    public void turnRight() {
        this.checkWorld("turnRight");
        this.setDirection(KarelWorld.rightFrom(this.getDirection()));
        this.getWorld().trace();
    }

    public void turnAround() {
        this.checkWorld("turnAround");
        this.setDirection(KarelWorld.oppositeDirection(this.getDirection()));
        this.getWorld().trace();
    }

    public void paintCorner(Color color) {
        KarelWorld world = this.getWorld();
        Point pt = this.getLocation();
        this.checkWorld("paintCorner");
        world.setCornerColor(pt.x, pt.y, color);
        world.trace();
    }

    public boolean random() {
        this.checkWorld("random");
        return this.random(0.5);
    }

    public boolean random(double p) {
        this.checkWorld("random");
        if (Math.random() < p) {
            return true;
        }
        return false;
    }

    public boolean cornerColorIs(Color color) {
        KarelWorld world = this.getWorld();
        Point pt = this.getLocation();
        this.checkWorld("cornerColorIs");
        if (color == null) {
            if (world.getCornerColor(pt.x, pt.y) == null) {
                return true;
            }
            return false;
        }
        return color.equals(world.getCornerColor(pt.x, pt.y));
    }
}

