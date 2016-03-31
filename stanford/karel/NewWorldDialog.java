/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.Platform;
import java.awt.FileDialog;
import java.awt.Frame;
import stanford.karel.KarelProgram;
import stanford.karel.KarelWorld;

class NewWorldDialog
extends FileDialog {
    public NewWorldDialog(KarelWorld world) {
        super(Platform.getEnclosingFrame(world), "New World", 1);
        this.setDirectory(KarelProgram.getWorldDirectory());
    }
}

