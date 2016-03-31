/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.ConsoleModel;
import acm.io.IOConsole;
import java.io.Writer;

class ConsoleWriter
extends Writer {
    private ConsoleModel model;

    public ConsoleWriter(ConsoleModel model) {
        this.model = model;
    }

    public void close() {
    }

    public void flush() {
    }

    public void write(char[] cbuf, int off, int len) {
        int eol;
        String str = new String(cbuf, off, len);
        int start = 0;
        while ((eol = str.indexOf(IOConsole.LINE_SEPARATOR, start)) != -1) {
            this.model.print(String.valueOf(str.substring(start, eol)) + "\n", 0);
            start = eol + IOConsole.LINE_SEPARATOR.length();
        }
        this.model.print(str.substring(start), 0);
    }
}

