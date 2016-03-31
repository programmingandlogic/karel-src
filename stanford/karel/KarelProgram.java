/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.program.Program;
import acm.util.AppletMenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.File;
import stanford.karel.Karel;
import stanford.karel.KarelControlPanel;
import stanford.karel.KarelErrorDialog;
import stanford.karel.KarelMenuBar;
import stanford.karel.KarelWorld;
import stanford.karel.KarelWorldMonitor;

public class KarelProgram
extends Program {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int INFINITE = 99999999;
    public static final int SIMPLE = 0;
    public static final int FANCY = 1;
    public static final Color BLACK = Color.black;
    public static final Color BLUE = Color.blue;
    public static final Color CYAN = Color.cyan;
    public static final Color DARK_GRAY = Color.darkGray;
    public static final Color GRAY = Color.gray;
    public static final Color GREEN = Color.green;
    public static final Color LIGHT_GRAY = Color.lightGray;
    public static final Color MAGENTA = Color.magenta;
    public static final Color ORANGE = Color.orange;
    public static final Color PINK = Color.pink;
    public static final Color RED = Color.red;
    public static final Color WHITE = Color.white;
    public static final Color YELLOW = Color.yellow;
    private KarelWorld world;
    private KarelControlPanel controlPanel;
    private KarelErrorDialog errorDialog;
    private AppletMenuBar menuBar;
    private boolean started;

    public KarelProgram() {
        this.world = this.createWorld();
        this.world.setRepaintFlag(false);
        this.world.init(10, 10);
        this.setLayout(new BorderLayout());
        this.add("Center", this.world);
        this.controlPanel = new KarelControlPanel(this);
        this.world.setMonitor(this.controlPanel);
        this.add("West", this.controlPanel);
        this.validate();
    }

    public void main() {
      this.startRun();
    }

    public KarelWorld getWorld() {
        return this.world;
    }

    public static String getWorldDirectory() {
        String dir = System.getProperty("user.dir");
        if (new File(dir, "worlds").isDirectory()) {
            dir = String.valueOf(dir) + "/worlds";
        }
        return dir;
    }

    protected KarelWorld createWorld() {
        return new KarelWorld();
    }

    protected boolean isStarted() {
        if (this.world == null || !super.isStarted()) {
            return false;
        }
        Dimension size = this.world.getSize();
        if (size == null || size.width == 0 || size.height == 0) {
            return false;
        }
        return true;
    }

    protected Karel getKarel() {
        return this.world.getKarel();
    }

    public void add(Karel karel) {
        this.add(karel, 1, 1, 1, 99999999);
    }

    public void add(Karel karel, int x, int y, int dir, int nBeepers) {
        karel.setLocation(x, y);
        karel.setDirection(dir);
        karel.setBeepersInBag(nBeepers);
        this.world.add(karel);
    }

    protected void setStartupObject(Object obj) {
        super.setStartupObject(obj);
    }

    protected void start(String[] args) {
        super.start(args);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void startRun() {
        Object className;
        this.menuBar = this.createMenuBar();
        this.menuBar.installInFrame(this.world);
        Karel karel = (Karel)this.getStartupObject();
        if (karel != null) {
            String classNameOld = karel.getClass().getName();
            classNameOld = classNameOld.substring(classNameOld.lastIndexOf(".") + 1);
            this.world.add(karel);
            try {
                File worldFile = new File(KarelProgram.getWorldDirectory(), String.valueOf(classNameOld) + ".w");
                if (worldFile.canRead()) {
                    this.world.load(worldFile);
                }
            }
            catch (Exception worldFile) {
                // empty catch block
            }
        }
        this.world.setRepaintFlag(true);
        this.world.repaint();
        do {
            this.started = false;
            className = this;
            synchronized (className) {
                while (!this.started) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException worldFile) {
                        // empty catch block
                    }
                }
            }
            try {
                if (karel == null) {
                    this.main();
                    continue;
                }
                karel.run();
                continue;
            }
            catch (Exception ex) {
                if (this.errorDialog == null) {
                    this.errorDialog = new KarelErrorDialog(this);
                }
                this.errorDialog.error(ex.getMessage());
                continue;
            }
        } while (true);
    }

    void signalStarted() {
        KarelProgram karelProgram = this;
        synchronized (karelProgram) {
            this.started = true;
            this.notifyAll();
        }
    }

    protected AppletMenuBar createMenuBar() {
        return new KarelMenuBar(this.world);
    }
}
