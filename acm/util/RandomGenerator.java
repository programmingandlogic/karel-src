/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import java.awt.Color;
import java.util.Random;

public class RandomGenerator
extends Random {
    public int nextInt(int n) {
        return this.nextInt(0, n - 1);
    }

    public int nextInt(int low, int high) {
        return low + (int)((double)(high - low + 1) * this.nextDouble());
    }

    public double nextDouble(double low, double high) {
        return low + (high - low) * this.nextDouble();
    }

    public boolean nextBoolean(double p) {
        if (this.nextDouble() < p) {
            return true;
        }
        return false;
    }

    public Color nextColor() {
        return new Color(this.nextInt(256), this.nextInt(256), this.nextInt(256));
    }
}

