/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

class CharacterQueue {
    private String buffer = "";
    private boolean isWaiting;

    public void enqueue(char ch) {
        CharacterQueue characterQueue = this;
        synchronized (characterQueue) {
            this.buffer = String.valueOf(this.buffer) + ch;
            this.notifyAll();
        }
    }

    public void enqueue(String str) {
        CharacterQueue characterQueue = this;
        synchronized (characterQueue) {
            this.buffer = String.valueOf(this.buffer) + str;
            this.notifyAll();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public char dequeue() {
        CharacterQueue characterQueue = this;
        synchronized (characterQueue) {
            do {
                if (this.buffer.length() != 0) {
                    char ch = this.buffer.charAt(0);
                    this.buffer = this.buffer.substring(1);
                    return ch;
                }
                try {
                    this.isWaiting = true;
                    this.wait();
                    this.isWaiting = false;
                }
                catch (InterruptedException var2_2) {
                    // empty catch block
                }
            } while (true);
        }
    }

    public boolean isWaiting() {
        return this.isWaiting;
    }
}

