/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.program.Program;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import stanford.karel.CardPanel;
import stanford.karel.HPanel;
import stanford.karel.Karel;
import stanford.karel.KarelProgram;
import stanford.karel.KarelResizer;
import stanford.karel.KarelWorld;
import stanford.karel.KarelWorldEditor;
import stanford.karel.KarelWorldMonitor;
import stanford.karel.LoadWorldDialog;
import stanford.karel.NewWorldDialog;
import stanford.karel.VPanel;

class KarelControlPanel
extends CardPanel
implements KarelWorldMonitor,
ActionListener,
AdjustmentListener {
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 18;
    private static final int BUTTON_SEP = 8;
    private static final int SPEEDBAR_WIDTH = 100;
    private static final int MAX_WIDTH = 50;
    private static final int MAX_HEIGHT = 50;
    private static final int SMALL_BUTTON_WIDTH = 60;
    private static final int MARGIN = 8;
    private static final int GAP = 5;
    private static final double SLOW_DELAY = 200.0;
    private static final double FAST_DELAY = 0.0;
    private KarelProgram program;
    private KarelWorld world;
    private KarelWorldEditor editor;
    private KarelResizer resizer;
    private Component buttonPanel;
    private Component editorPanel;
    private Component resizePanel;
    private Button startButton;
    private Button loadWorldButton;
    private Button newWorldButton;
    private Button editWorldButton;
    private Button saveWorldButton;
    private Button dontSaveButton;
    private Button okButton;
    private Button cancelButton;
    private Scrollbar speedBar;
    private double speed;

    public KarelControlPanel(KarelProgram program) {
        this.program = program;
        this.world = program.getWorld();
        this.editor = this.createEditor();
        this.resizer = this.createResizer();
        this.editorPanel = this.createEditorPanel();
        this.add("editor", this.editorPanel);
        this.resizePanel = this.createResizePanel();
        this.add("resize", this.resizePanel);
        this.buttonPanel = this.createButtonPanel();
        this.add("buttons", this.buttonPanel);
        this.setView("buttons");
    }

    public KarelWorld getWorld() {
        return this.world;
    }

    public KarelProgram getProgram() {
        return this.program;
    }

    public KarelWorldEditor getEditor() {
        return this.editor;
    }

    public KarelResizer getResizer() {
        return this.resizer;
    }

    public Dimension getPreferredSize() {
        return new Dimension(250, 1);
    }

    public void startWorldEdit() {
    }

    public void endWorldEdit() {
    }

    public void wallAction(Point pt, int dir) {
        this.editor.wallAction(pt, dir);
    }

    public void cornerAction(Point pt) {
        this.editor.cornerAction(pt);
    }

    public void trace() {
        double delay = 200.0 + Math.sqrt(this.speed) * -200.0;
        if (this.speed < 0.98) {
            Program.pause(delay);
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        this.speedBar.setValue((int)Math.round(100.0 * speed));
    }

    public double getSpeed() {
        return this.speed;
    }

    protected KarelWorldEditor createEditor() {
        return new KarelWorldEditor(this.getWorld());
    }

    protected Component createEditorPanel() {
        VPanel vbox = new VPanel();
        vbox.add("", this.editor);
        this.saveWorldButton = new Button("Save World");
        this.saveWorldButton.addActionListener(this);
        vbox.add("/width:100/height:18/top:8", this.saveWorldButton);
        this.dontSaveButton = new Button("Don't Save");
        this.dontSaveButton.addActionListener(this);
        vbox.add("/width:100/height:18/top:8", this.dontSaveButton);
        return vbox;
    }

    protected Component createButtonPanel() {
        VPanel vbox = new VPanel();
        this.startButton = new Button("Start Program");
        this.startButton.addActionListener(this);
        vbox.add("/center/width:100/height:18", this.startButton);
        this.loadWorldButton = new Button("Load World");
        this.loadWorldButton.addActionListener(this);
        vbox.add("/center/width:100/height:18/top:8", this.loadWorldButton);
        this.newWorldButton = new Button("New World");
        this.newWorldButton.addActionListener(this);
        vbox.add("/center/width:100/height:18/top:8", this.newWorldButton);
        this.editWorldButton = new Button("Edit World");
        this.editWorldButton.addActionListener(this);
        vbox.add("/center/width:100/height:18/top:8", this.editWorldButton);
        HPanel hbox = new HPanel();
        this.speedBar = new Scrollbar(0);
        this.speedBar.addAdjustmentListener(this);
        this.speedBar.setBlockIncrement(10);
        this.speedBar.setValues(0, 1, 0, 100);
        hbox.add("/center", new Label("Slow "));
        hbox.add("/center/width:100", this.speedBar);
        hbox.add("/center", new Label(" Fast"));
        vbox.add("/center/top:8", hbox);
        return vbox;
    }

    protected Component createResizePanel() {
        VPanel vbox = new VPanel();
        this.cancelButton = new Button("Cancel");
        this.cancelButton.addActionListener(this);
        this.okButton = new Button("OK");
        this.okButton.addActionListener(this);
        vbox.add("", this.getResizer());
        vbox.add("/center/width:60/space:5", this.okButton);
        vbox.add("/center/width:60/space:5", this.cancelButton);
        return vbox;
    }

    protected KarelResizer createResizer() {
        return new KarelResizer();
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        if (source == this.startButton) {
            this.program.signalStarted();
        } else if (source == this.loadWorldButton) {
            LoadWorldDialog dialog = new LoadWorldDialog(this.world);
            dialog.setVisible(true);
            String fileName = dialog.getFile();
            if (fileName != null) {
                this.world.load(String.valueOf(dialog.getDirectory()) + "/" + fileName);
            }
        } else if (source == this.newWorldButton) {
            this.setView("resize");
        } else if (source == this.editWorldButton) {
            this.world.setEditMode(true);
            this.editor.initEditorCanvas();
            this.setView("editor");
        } else if (source == this.saveWorldButton) {
            if (this.world.getPathname() == null) {
                NewWorldDialog dialog = new NewWorldDialog(this.world);
                dialog.setVisible(true);
                String fileName = dialog.getFile();
                if (fileName != null) {
                    this.world.setPathName(String.valueOf(dialog.getDirectory()) + "/" + fileName);
                }
            }
            this.world.save();
            this.world.setEditMode(false);
            this.setView("buttons");
        } else if (source == this.dontSaveButton) {
            this.world.setEditMode(false);
            this.setView("buttons");
        } else if (source == this.cancelButton) {
            this.setView("buttons");
        } else if (source == this.okButton) {
            NewWorldDialog dialog = new NewWorldDialog(this.world);
            dialog.setVisible(true);
            String fileName = dialog.getFile();
            if (fileName == null) {
                this.setView("buttons");
            } else {
                this.world.init(this.resizer.getColumns(), this.resizer.getRows());
                this.world.setPathName(String.valueOf(dialog.getDirectory()) + "/" + fileName);
                this.world.setEditMode(true);
                if (this.world.getKarelCount() == 1) {
                    Karel karel = this.world.getKarel();
                    karel.setLocation(1, 1);
                    karel.setDirection(1);
                    karel.setBeepersInBag(99999999);
                    this.world.repaint();
                }
                this.editor.initEditorCanvas();
                this.setView("editor");
            }
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        Component source = (Component)e.getSource();
        if (source == this.speedBar) {
            this.speed = (double)this.speedBar.getValue() / 100.0;
        }
    }
}

