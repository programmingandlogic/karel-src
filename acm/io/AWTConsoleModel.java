/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.BufferedConsoleModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.PrintJob;
import java.awt.TextArea;
import java.awt.event.KeyListener;
import java.io.BufferedReader;

class AWTConsoleModel
extends BufferedConsoleModel {
    private TextArea textArea = new TextArea();
    private Object lock;
    private BufferedReader inputScript;
    private int base;

    public AWTConsoleModel() {
        this.textArea.addKeyListener(this);
    }

    public void clear() {
        this.textArea.setText("");
    }

    public String getText() {
        return this.textArea.getText();
    }

    public String getText(int start, int end) {
        return this.textArea.getText().substring(start, end);
    }

    public int getLength() {
        return this.getText().length();
    }

    public Component getConsolePane() {
        return this.textArea;
    }

    public Component getTextPane() {
        return this.textArea;
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

    public void setInputStyle(int style) {
    }

    public void setInputColor(Color color) {
    }

    public void setErrorStyle(int style) {
    }

    public void setErrorColor(Color color) {
    }

    protected void insert(String str, int dot, int style) {
        this.textArea.insert(str, dot);
    }

    protected void delete(int p1, int p2) {
        this.textArea.replaceRange("", p1, p2);
    }

    protected void setCaretPosition(int pos) {
        this.textArea.setCaretPosition(pos);
    }

    protected int getCaretPosition() {
        return this.textArea.getCaretPosition();
    }

    protected void select(int p1, int p2) {
        this.textArea.select(p1, p2);
    }

    protected int getSelectionStart() {
        return this.textArea.getSelectionStart();
    }

    protected int getSelectionEnd() {
        return this.textArea.getSelectionEnd();
    }
}

