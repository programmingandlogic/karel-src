/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.graphics.GDimension;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import acm.util.ErrorException;
import java.lang.reflect.Method;

class GObjectProgram
extends GraphicsProgram {
    GObjectProgram() {
    }

    protected void runHook() {
        GObject gobj = (GObject)this.getStartupObject();
        GDimension size = gobj.getSize();
        this.add(gobj, ((double)this.getWidth() - size.getWidth()) / 2.0, ((double)this.getHeight() - size.getHeight()) / 2.0);
        try {
            Class gobjClass = gobj.getClass();
            String className = gobjClass.getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            this.setTitle(className);
            Method run = gobjClass.getMethod("run", new Class[0]);
            if (run == null) {
                throw new ErrorException(String.valueOf(className) + " has no run method");
            }
            run.invoke(gobj, new Object[0]);
        }
        catch (Exception ex) {
            throw new ErrorException(ex);
        }
    }
}

