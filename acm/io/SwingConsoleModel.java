/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.BufferedConsoleModel;
import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.PrintJob;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

class SwingConsoleModel
extends BufferedConsoleModel {
    private int base;
    private SimpleAttributeSet inputAttributes;
    private SimpleAttributeSet errorAttributes;
    private Object lock;
    private BufferedReader inputScript;
    private JScrollPane consolePane = new JScrollPane(22, 32);
    private JTextPane textPane = new JTextPane();
    private Document document;
    private String lineSeparator;

    public SwingConsoleModel() {
        this.textPane.addKeyListener(this);
        this.consolePane.setViewportView(this.textPane);
        this.document = this.textPane.getDocument();
        this.lineSeparator = System.getProperty("line.separator");
        this.inputAttributes = new SimpleAttributeSet();
        this.inputAttributes.addAttribute(StyleConstants.Foreground, Color.blue);
        this.inputAttributes.addAttribute(StyleConstants.Bold, Boolean.TRUE);
        this.errorAttributes = new SimpleAttributeSet();
        this.errorAttributes.addAttribute(StyleConstants.Foreground, Color.red);
    }

    public void clear() {
        this.textPane.setText("");
    }

    public String getText() {
        return this.textPane.getText();
    }

    public String getText(int start, int end) {
        try {
            return this.document.getText(start, end - start);
        }
        catch (BadLocationException ex) {
            throw new ErrorException(ex);
        }
    }

    public int getLength() {
        return this.document.getLength();
    }

    public Component getConsolePane() {
        return this.consolePane;
    }

    public Component getTextPane() {
        return this.textPane;
    }

    public void cut() {
        this.copy();
        this.deleteSelection();
    }

    public void copy() {
        this.textPane.copy();
    }

    public void paste() {
        int start = this.deleteSelection();
        this.textPane.setSelectionStart(start);
        this.textPane.paste();
        if (this.document instanceof DefaultStyledDocument) {
            DefaultStyledDocument doc = (DefaultStyledDocument)this.document;
            doc.setCharacterAttributes(start, this.textPane.getSelectionEnd() - start, this.inputAttributes, true);
        }
    }

    public void selectAll() {
        this.textPane.selectAll();
    }

    public void print(PrintJob pj) {
        String text = this.getText();
        Dimension pageSize = pj.getPageDimension();
        Graphics g = pj.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        Insets margins = this.textPane.getInsets();
        int fontHeight = fm.getHeight();
        int linesPerPage = pageSize.height / fontHeight;
        int nLines = this.countLines(text);
        int nPages = 1 + (nLines - 1) / linesPerPage;
        int top = 0;
        int page = 1;
        while (page <= nPages) {
            if (page > 1) {
                g = pj.getGraphics();
            }
            int bottom = this.getLineY(text, 1 + page * linesPerPage);
            g.translate(0, - top);
            g.setClip(0, top, pageSize.width, bottom - top);
            this.textPane.paint(g);
            g.dispose();
            top = bottom;
            ++page;
        }
    }

    public void setInputStyle(int style) {
        boolean isBold = (style & 1) != 0;
        boolean isItalic = (style & 2) != 0;
        this.inputAttributes.addAttribute(StyleConstants.Bold, new Boolean(isBold));
        this.inputAttributes.addAttribute(StyleConstants.Italic, new Boolean(isItalic));
        this.textPane.repaint();
    }

    public void setInputColor(Color color) {
        this.inputAttributes.addAttribute(StyleConstants.Foreground, color);
        this.textPane.repaint();
    }

    public void setErrorStyle(int style) {
        boolean isBold = (style & 1) != 0;
        boolean isItalic = (style & 2) != 0;
        this.errorAttributes.addAttribute(StyleConstants.Bold, new Boolean(isBold));
        this.errorAttributes.addAttribute(StyleConstants.Italic, new Boolean(isItalic));
        this.textPane.repaint();
    }

    public void setErrorColor(Color color) {
        this.errorAttributes.addAttribute(StyleConstants.Foreground, color);
        this.textPane.repaint();
    }

    protected void insert(String str, int dot, int style) {
        SimpleAttributeSet attributes = null;
        switch (style) {
            case 0: {
                attributes = new SimpleAttributeSet();
                int fontStyle = this.textPane.getFont().getStyle();
                boolean isBold = (fontStyle & 1) != 0;
                boolean isItalic = (fontStyle & 2) != 0;
                attributes.addAttribute(StyleConstants.Bold, new Boolean(isBold));
                attributes.addAttribute(StyleConstants.Italic, new Boolean(isItalic));
                attributes.addAttribute(StyleConstants.Foreground, this.textPane.getForeground());
                break;
            }
            case 1: {
                attributes = this.inputAttributes;
                break;
            }
            case 2: {
                attributes = this.errorAttributes;
            }
        }
        try {
            this.document.insertString(dot, str, attributes);
        }
        catch (BadLocationException fontStyle) {
            // empty catch block
        }
    }

    protected void delete(int p1, int p2) {
        try {
            this.document.remove(p1, p2 - p1);
        }
        catch (BadLocationException ex) {
            throw new ErrorException(ex);
        }
    }

    protected void setCaretPosition(int pos) {
        this.textPane.setCaretPosition(pos);
    }

    protected int getCaretPosition() {
        return this.textPane.getCaretPosition();
    }

    protected void select(int p1, int p2) {
        this.textPane.select(p1, p2);
    }

    protected int getSelectionStart() {
        return this.textPane.getSelectionStart();
    }

    protected int getSelectionEnd() {
        return this.textPane.getSelectionEnd();
    }

    private int countLines(String text) {
        int count = 0;
        int pos = -1;
        while ((pos = text.indexOf(this.lineSeparator, pos + 1)) != -1) {
            ++count;
        }
        if (!text.endsWith(this.lineSeparator)) {
            ++count;
        }
        return count;
    }

    private int getLineY(String text, int lineNumber) {
        if (text.length() == 0) {
            return 0;
        }
        int pos = 0;
        try {
            int i = 1;
            while (i < lineNumber) {
                int eol = text.indexOf(this.lineSeparator, pos);
                if (eol == -1) {
                    Rectangle bounds = this.textPane.modelToView(pos);
                    return bounds.y + bounds.height;
                }
                pos = eol + this.lineSeparator.length();
                ++i;
            }
            return this.textPane.modelToView((int)pos).y;
        }
        catch (Exception ex) {
            throw new ErrorException("Internal error: " + ex);
        }
    }
}

