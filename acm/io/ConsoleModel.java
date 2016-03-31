/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import java.awt.Color;
import java.awt.Component;
import java.awt.PrintJob;
import java.io.BufferedReader;

interface ConsoleModel {
    public static final int OUTPUT_STYLE = 0;
    public static final int INPUT_STYLE = 1;
    public static final int ERROR_STYLE = 2;

    public void print(String var1, int var2);

    public String readLine();

    public void clear();

    public String getText();

    public String getText(int var1, int var2);

    public int getLength();

    public Component getConsolePane();

    public Component getTextPane();

    public void cut();

    public void copy();

    public void paste();

    public void selectAll();

    public void print(PrintJob var1);

    public void setInputStyle(int var1);

    public void setInputColor(Color var1);

    public void setErrorStyle(int var1);

    public void setErrorColor(Color var1);

    public void setInputScript(BufferedReader var1);

    public BufferedReader getInputScript();
}

