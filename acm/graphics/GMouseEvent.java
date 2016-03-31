/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import java.awt.Component;
import java.awt.event.MouseEvent;

class GMouseEvent
extends MouseEvent {
    private Object effectiveSource;

    public GMouseEvent(Object source, int id, MouseEvent e) {
        super(e.getComponent(), id, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
        this.effectiveSource = source;
    }

    public Object getSource() {
        return this.effectiveSource;
    }

    public Component getComponent() {
        return (Component)super.getSource();
    }

    public String toString() {
        return String.valueOf(this.getClass().getName()) + "[" + this.paramString() + "] on " + this.getSource();
    }
}

