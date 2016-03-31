/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.ConsoleModel;
import java.io.Reader;

class ConsoleReader
extends Reader {
    private ConsoleModel model;
    private String buffer;

    public ConsoleReader(ConsoleModel model) {
        this.model = model;
        this.buffer = null;
    }

    public void close() {
    }

    public int read(char[] cbuf, int off, int len) {
        if (len == 0) {
            return 0;
        }
        if (this.buffer == null) {
            this.buffer = this.model.readLine();
            if (this.buffer == null) {
                return -1;
            }
            this.buffer = String.valueOf(this.buffer) + "\n";
        }
        if (len < this.buffer.length()) {
            this.buffer.getChars(0, len, cbuf, off);
            this.buffer = this.buffer.substring(len);
        } else {
            len = this.buffer.length();
            this.buffer.getChars(0, len, cbuf, off);
            this.buffer = null;
        }
        return len;
    }
}

