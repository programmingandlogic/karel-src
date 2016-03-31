/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

public class Animator
extends Thread {
    private boolean terminationRequested;

    public Animator() {
    }

    public Animator(ThreadGroup group) {
        super(group, (Runnable)null);
    }

    public void pause(double milliseconds) {
        if (this.terminationRequested) {
            throw new ThreadDeath();
        }
        try {
            int millis = (int)milliseconds;
            int nanos = (int)Math.round((milliseconds - (double)millis) * 1000000.0);
            Thread.sleep(millis, nanos);
        }
        catch (InterruptedException millis) {
            // empty catch block
        }
    }

    public void requestTermination() {
        this.terminationRequested = true;
    }

    public void checkForTermination() {
        if (this.terminationRequested) {
            throw new ThreadDeath();
        }
        Thread.yield();
    }
}

