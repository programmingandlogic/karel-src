/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Point;

interface KarelWorldMonitor {
    public void startWorldEdit();

    public void endWorldEdit();

    public void wallAction(Point var1, int var2);

    public void cornerAction(Point var1);

    public void trace();

    public void setSpeed(double var1);

    public double getSpeed();
}

