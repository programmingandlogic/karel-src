/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Panel;

class CardPanel
extends Panel {
    private CardLayout layout = new CardLayout();
    private String currentView;

    public CardPanel() {
        this.setLayout(this.layout);
    }

    public void setView(String name) {
        this.validate();
        this.currentView = name;
        this.layout.show(this, name);
        this.repaint();
    }

    public String getView() {
        return this.currentView;
    }
}

