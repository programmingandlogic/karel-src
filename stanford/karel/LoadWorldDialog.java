/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.Platform;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import stanford.karel.KarelProgram;
import stanford.karel.KarelWorld;

class LoadWorldDialog
extends FileDialog
implements FilenameFilter {
    public LoadWorldDialog(KarelWorld world) {
        super(Platform.getEnclosingFrame(world), "Load World");
        this.setDirectory(KarelProgram.getWorldDirectory());
        this.setFilenameFilter(this);
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(".w");
    }
}

