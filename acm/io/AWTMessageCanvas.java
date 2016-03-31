/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

class AWTMessageCanvas
extends Canvas {
    public static final int MARGIN = 8;
    public static final Font MESSAGE_FONT = new Font("Dialog", 0, 12);
    private String msg;

    public AWTMessageCanvas() {
        this.setFont(MESSAGE_FONT);
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void paint(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = 8;
        int y = 8 + fm.getAscent();
        int limit = this.getSize().width - 8;
        StringTokenizer tokenizer = new StringTokenizer(this.msg, " ", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int width = fm.stringWidth(token);
            if (x + width > limit) {
                x = 8;
                y += fm.getHeight();
                if (token.equals(" ")) continue;
            }
            g.drawString(token, x, y);
            x += width;
        }
    }
}

