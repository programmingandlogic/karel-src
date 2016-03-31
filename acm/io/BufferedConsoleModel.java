/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.CharacterQueue;
import acm.io.ConsoleModel;
import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Component;
import java.awt.PrintJob;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;

abstract class BufferedConsoleModel
implements KeyListener,
ConsoleModel {
    private CharacterQueue buffer = new CharacterQueue();
    private BufferedReader inputScript;
    private Object lock = new Object();
    private int base = 0;

    public void print(String str, int style) {
        Object object = this.lock;
        synchronized (object) {
            int dot = this.getLength();
            this.insert(str, dot, style);
            this.base = this.getLength();
            this.setCaretPosition(this.base);
        }
    }

    public String readLine() {
        Object object = this.lock;
        synchronized (object) {
            char ch;
            this.base = this.getLength();
            if (this.inputScript != null) {
                String line = null;
                try {
                    line = this.inputScript.readLine();
                }
                catch (IOException ex) {
                    throw new ErrorException(ex);
                }
                if (line != null) {
                    this.insert(line, this.base, 1);
                    this.insert("\n", this.base + line.length(), 0);
                    return line;
                }
                try {
                    this.inputScript.close();
                }
                catch (IOException ex) {
                    // empty catch block
                }
                this.inputScript = null;
            }
            this.setCaretPosition(this.base);
            while ((ch = this.buffer.dequeue()) != '\n' && ch != '\r') {
                int dot;
                if (this.getCaretPosition() < this.base) {
                    this.setCaretPosition(this.getLength());
                }
                switch (ch) {
                    case '\b': 
                    case '': {
                        dot = this.getSelectionStart();
                        if (dot == this.getSelectionEnd()) {
                            if (dot <= this.base) break;
                            this.delete(dot - 1, 1);
                            --dot;
                            break;
                        }
                        dot = this.deleteSelection();
                        break;
                    }
                    case '\u0002': {
                        dot = Math.max(this.getSelectionStart() - 1, this.base);
                        break;
                    }
                    case '\u0006': {
                        dot = Math.min(this.getSelectionEnd() + 1, this.getLength());
                        break;
                    }
                    default: {
                        dot = this.getSelectionStart();
                        if (dot != this.getSelectionEnd()) {
                            dot = this.deleteSelection();
                        }
                        this.insert("" + ch, dot, 1);
                        ++dot;
                    }
                }
                this.select(dot, dot);
                this.setCaretPosition(dot);
            }
            int len = this.getLength() - this.base;
            this.insert("\n", this.base + len, 0);
            return this.getText(this.base, this.base + len);
        }
    }

    public void setInputScript(BufferedReader rd) {
        this.inputScript = rd;
        if (this.buffer.isWaiting()) {
            try {
                String line = this.inputScript.readLine();
                this.buffer.enqueue(String.valueOf(line) + "\n");
            }
            catch (IOException ex) {
                throw new ErrorException(ex);
            }
        }
    }

    public BufferedReader getInputScript() {
        return this.inputScript;
    }

    protected int deleteSelection() {
        int start = Math.max(this.base, this.getSelectionStart());
        int end = this.getSelectionEnd();
        if (end <= this.base) {
            return this.getLength();
        }
        this.delete(start, end);
        return start;
    }

    public abstract void clear();

    public abstract String getText();

    public abstract int getLength();

    public abstract Component getConsolePane();

    public abstract Component getTextPane();

    public abstract void cut();

    public abstract void copy();

    public abstract void paste();

    public abstract void selectAll();

    public abstract void print(PrintJob var1);

    public abstract void setInputStyle(int var1);

    public abstract void setInputColor(Color var1);

    public abstract void setErrorStyle(int var1);

    public abstract void setErrorColor(Color var1);

    public void keyTyped(KeyEvent e) {
        this.buffer.enqueue(e.getKeyChar());
        e.consume();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37: {
                this.buffer.enqueue('\u0002');
                break;
            }
            case 39: {
                this.buffer.enqueue('\u0006');
            }
        }
        e.consume();
    }

    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    protected abstract void insert(String var1, int var2, int var3);

    protected abstract void delete(int var1, int var2);

    protected abstract void setCaretPosition(int var1);

    protected abstract int getCaretPosition();

    protected abstract void select(int var1, int var2);

    protected abstract int getSelectionStart();

    protected abstract int getSelectionEnd();
}

