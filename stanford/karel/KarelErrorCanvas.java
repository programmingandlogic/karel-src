/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

class KarelErrorCanvas
extends Canvas {
    private static final int LEFT_MARGIN = 5;
    private static final int RIGHT_MARGIN = 5;
    private static final int TOP_MARGIN = 40;
    private String errorText;

    public void setText(String msg) {
        this.errorText = msg;
        this.repaint();
    }

    public void paint(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = 5;
        int y = 40 + fm.getAscent();
        int height = fm.getHeight();
        int limit = this.getSize().width - 5;
        StringTokenizer tokenizer = new StringTokenizer(this.errorText, " ", true);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            int width = fm.stringWidth(word);
            if (x + width > limit && x > 5 && !word.equals(" ")) {
                x = 5;
                y += height;
            }
            g.drawString(word, x, y);
            x += width;
        }
    }
}

