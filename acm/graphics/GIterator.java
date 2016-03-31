/*
 * Decompiled with CFR 0_114.
 */
package acm.graphics;

import acm.graphics.GContainer;
import acm.graphics.GObject;
import acm.util.ErrorException;
import java.util.Iterator;

class GIterator
implements Iterator {
    private GContainer container;
    private int direction;
    private int index;
    private int nElements;

    public GIterator(GContainer container, int direction) {
        switch (direction) {
            case 0: 
            case 1: {
                this.direction = direction;
                break;
            }
            default: {
                throw new ErrorException("Illegal direction for iterator");
            }
        }
        this.container = container;
        this.index = 0;
        this.nElements = container.getElementCount();
    }

    public boolean hasNext() {
        if (this.index < this.nElements) {
            return true;
        }
        return false;
    }

    public Object next() {
        if (this.direction == 1) {
            return this.container.getElement(this.nElements - this.index++ - 1);
        }
        return this.container.getElement(this.index++);
    }

    public GObject nextElement() {
        return (GObject)this.next();
    }

    public void remove() {
        if (this.direction == 1) {
            this.container.remove(this.container.getElement(this.nElements - --this.index - 1));
        } else {
            this.container.remove(this.container.getElement(--this.index));
        }
        --this.nElements;
    }
}

