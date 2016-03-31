/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.ConsoleModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.PrintJob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class SystemConsoleModel
implements ConsoleModel {
    private BufferedReader inputScript;
    private String text = "";

    public void clear() {
    }

    public void print(String str, int style) {
        System.out.print(str);
        this.text = String.valueOf(this.text) + str;
    }

    public String readLine() {
        System.out.flush();
        String line = "";
        try {
            do {
                int ch;
                if ((ch = this.inputScript == null ? System.in.read() : this.inputScript.read()) == -1 && line.length() == 0) {
                    try {
                        this.inputScript.close();
                    }
                    catch (IOException var3_3) {
                        // empty catch block
                    }
                    this.inputScript = null;
                    continue;
                }
                if (ch != -1 && ch != 10) {
                    line = String.valueOf(line) + (char)ch;
                    continue;
                }
                break;
            } while (true);
        }
        catch (IOException var3_4) {
            // empty catch block
        }
        if (this.inputScript != null) {
            this.print(String.valueOf(line) + "\n", 1);
        }
        return line;
    }

    public String getText() {
        return this.text;
    }

    public String getText(int start, int end) {
        return this.text.substring(start, end);
    }

    public int getLength() {
        return this.text.length();
    }

    public Component getConsolePane() {
        return null;
    }

    public Component getTextPane() {
        return null;
    }

    public void setInputStyle(int style) {
    }

    public void setInputColor(Color color) {
    }

    public void setErrorStyle(int style) {
    }

    public void setErrorColor(Color color) {
    }

    public void cut() {
    }

    public void copy() {
    }

    public void paste() {
    }

    public void selectAll() {
    }

    public void print(PrintJob pj) {
    }

    public void setInputScript(BufferedReader rd) {
        this.inputScript = rd;
    }

    public BufferedReader getInputScript() {
        return this.inputScript;
    }
}

