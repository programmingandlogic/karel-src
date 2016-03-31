/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import java.io.InputStream;

class HexInputStream
extends InputStream {
    private String[] hexData;
    private int arrayIndex;
    private int charIndex;

    public HexInputStream(String[] hexData) {
        this.hexData = hexData;
        this.arrayIndex = 0;
        this.charIndex = 0;
    }

    public int read() {
        if (this.arrayIndex >= this.hexData.length) {
            return -1;
        }
        if (this.charIndex >= this.hexData[this.arrayIndex].length()) {
            ++this.arrayIndex;
            this.charIndex = 0;
            return this.read();
        }
        int data = Character.digit(this.hexData[this.arrayIndex].charAt(this.charIndex++), 16) << 4;
        return data |= Character.digit(this.hexData[this.arrayIndex].charAt(this.charIndex++), 16);
    }
}

