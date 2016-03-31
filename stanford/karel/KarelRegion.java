/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.ErrorException;
import java.awt.Polygon;

class KarelRegion {
    public static final double EPSILON = 1.0E-11;
    private Polygon p = new Polygon();
    private double x = 0.0;
    private double y = 0.0;

    public Polygon getPolygon() {
        return this.p;
    }

    public void setOrigin(int x, int y, double dx, double dy, int dir) {
        if (this.p.npoints != 0) {
            throw new ErrorException("setOrigin called on nonempty region");
        }
        this.x = x;
        this.y = y;
        this.addVector(dx, dy, dir);
    }

    public void addVector(double dx, double dy, int dir) {
        switch (dir) {
            case 1: {
                this.x += dx;
                this.y -= dy;
                break;
            }
            case 0: {
                this.x -= dy;
                this.y -= dx;
                break;
            }
            case 3: {
                this.x -= dx;
                this.y += dy;
                break;
            }
            case 2: {
                this.x += dy;
                this.y += dx;
            }
        }
        this.p.addPoint((int)Math.round(this.x + 1.0E-11), (int)Math.round(this.y + 1.0E-11));
    }

    public int getCurrentX() {
        return this.p.xpoints[this.p.npoints - 1];
    }

    public int getCurrentY() {
        return this.p.ypoints[this.p.npoints - 1];
    }
}

