/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Color;
import java.awt.Point;

class MapTool {
    public int toolClass;
    public int x;
    public int y;
    public int dir;
    public int size;
    public int beeperDelta;
    public String label;
    public Color color;

    public MapTool(int toolClass, int x, int y, int size) {
        this.toolClass = toolClass;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean contains(Point pt) {
        if (pt.x >= this.x && pt.x < this.x + this.size && pt.y >= this.y && pt.y < this.y + this.size) {
            return true;
        }
        return false;
    }
}

