/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.io.IOConsole;
import acm.io.IODialog;
import acm.io.IOModel;
import acm.program.ProgramAppletStub;
import acm.program.ProgramBackgroundPane;
import acm.program.ProgramBorder;
import acm.program.ProgramContentPane;
import acm.program.ProgramFrame;
import acm.program.ProgramGlassPane;
import acm.program.ProgramRootPane;
import acm.program.ProgramStartupListener;
import acm.program.ProgramWindowListener;
import acm.util.ErrorException;
import acm.util.MediaTools;
import acm.util.Platform;
import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class Program
extends Applet
implements IOModel,
Runnable {
    private static final int STARTUP_DELAY = 1000;
    private static final int STARTUP_CYCLE = 300;
    private static final int DEFAULT_X = 16;
    private static final int DEFAULT_Y = 40;
    private static final int DEFAULT_WIDTH = 754;
    private static final int DEFAULT_HEIGHT = 492;
    private static final Color DEFAULT_BGCOLOR = Color.white;
    private Frame programFrame;
    private Hashtable optionTable;
    private AppletStub stub;
    private String title;
    private MenuBar menuBar;
    private Hashtable parameterTable = null;
    private ProgramRootPane rootPane;
    private ProgramBackgroundPane backgroundPane;
    private ProgramGlassPane glassPane;
    private Container contentPane;
    private Component northBorder;
    private Component southBorder;
    private Component eastBorder;
    private Component westBorder;
    private IOConsole console;
    private IODialog dialog;
    private Object startupObject;
    private boolean started;
    private boolean shown = false;
    private boolean isAppletMode;

    protected Program() {
        this.title = this.getClass().getName();
        this.title = this.title.substring(this.title.lastIndexOf(".") + 1);
        this.stub = new ProgramAppletStub(this);
        this.setAppletStub(this.stub);
        this.setVisible(false);
        this.setLayout(new BorderLayout());
        this.initContentPane();
        this.initRootPane();
        this.console = this.createConsole();
        this.dialog = this.createDialogIO();
        this.dialog.setAssociatedConsole(this.console);
    }

    public void run() {
    }

    public void print(String value) {
        this.getOutputModel().print(value);
    }

    public final void print(boolean x) {
        this.print("" + x);
    }

    public final void print(char x) {
        this.print("" + x);
    }

    public final void print(double x) {
        this.print("" + x);
    }

    public final void print(float x) {
        this.print("" + x);
    }

    public final void print(int x) {
        this.print("" + x);
    }

    public final void print(long x) {
        this.print("" + x);
    }

    public final void print(Object x) {
        this.print((String)x);
    }

    public void println() {
        this.getOutputModel().println();
    }

    public void println(String value) {
        this.getOutputModel().println(value);
    }

    public final void println(boolean x) {
        this.println("" + x);
    }

    public final void println(char x) {
        this.println("" + x);
    }

    public final void println(double x) {
        this.println("" + x);
    }

    public final void println(float x) {
        this.println("" + x);
    }

    public final void println(int x) {
        this.println("" + x);
    }

    public final void println(long x) {
        this.println("" + x);
    }

    public final void println(Object x) {
        this.println((String)x);
    }

    public void showErrorMessage(String msg) {
        this.getDialog().showErrorMessage(msg);
    }

    public final String readLine() {
        return this.readLine(null);
    }

    public String readLine(String prompt) {
        return this.getInputModel().readLine(prompt);
    }

    public final int readInt() {
        return this.readInt(null);
    }

    public int readInt(String prompt) {
        return this.getInputModel().readInt(prompt);
    }

    public final double readDouble() {
        return this.readDouble(null);
    }

    public double readDouble(String prompt) {
        return this.getInputModel().readDouble(prompt);
    }

    public final boolean readBoolean() {
        return this.readBoolean(null);
    }

    public final boolean readBoolean(String prompt) {
        return this.readBoolean(prompt, "true", "false");
    }

    public boolean readBoolean(String prompt, String trueLabel, String falseLabel) {
        return this.getInputModel().readBoolean(prompt, trueLabel, falseLabel);
    }

    public boolean isAppletMode() {
        if (!this.started) {
            throw new ErrorException("You can't call isAppletMode from the constructor");
        }
        return this.isAppletMode;
    }

    public IOConsole getConsole() {
        return this.console;
    }

    public IODialog getDialog() {
        return this.dialog;
    }

    public IOModel getInputModel() {
        return this.getConsole();
    }

    public IOModel getOutputModel() {
        return this.getConsole();
    }

    public BufferedReader getReader() {
        return this.getConsole().getReader();
    }

    public PrintWriter getWriter() {
        return this.getConsole().getWriter();
    }

    public void setInputScript(BufferedReader rd) {
        this.getConsole().setInputScript(rd);
    }

    public void setTitle(String title) {
        this.title = title;
        if (this.programFrame != null) {
            this.programFrame.setTitle(title);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public static void pause(double milliseconds) {
        try {
            int millis = (int)milliseconds;
            int nanos = (int)Math.round((milliseconds - (double)millis) * 1000000.0);
            Thread.sleep(millis, nanos);
        }
        catch (InterruptedException millis) {
            // empty catch block
        }
    }

    protected Frame createProgramFrame() {
        ProgramFrame frame = new ProgramFrame(this.getTitle());
        frame.setLayout(new BorderLayout());
        return frame;
    }

    protected IOConsole createConsole() {
        return IOConsole.SYSTEM_CONSOLE;
    }

    protected IODialog createDialogIO() {
        return new IODialog(this.getContentPane());
    }

    protected Component createBorder(String side) {
        return this.isAppletMode() ? new ProgramBorder(side, 1) : null;
    }

    public int getWidth() {
        return this.contentPane.getSize().width;
    }

    public int getHeight() {
        return this.contentPane.getSize().height;
    }

    public String getParameter(String name) {
        String value = null;
        if (this.parameterTable != null) {
            value = (String)this.parameterTable.get(name.toLowerCase());
        }
        if (value != null) {
            return value;
        }
        return super.getParameter(name);
    }

    public void setLayout(LayoutManager layout) {
        if (this.contentPane == null) {
            super.setLayout(layout);
        } else {
            this.contentPane.setLayout(layout);
        }
    }

    public LayoutManager getLayout() {
        return this.contentPane.getLayout();
    }

    public Component add(Component comp) {
        this.contentPane.add(comp);
        if (!this.shown && this.programFrame != null) {
            this.programFrame.show();
            this.shown = true;
        }
        return comp;
    }

    public Component add(String name, Component comp) {
        this.contentPane.add(name, comp);
        if (!this.shown && this.programFrame != null) {
            this.programFrame.show();
            this.shown = true;
        }
        return comp;
    }

    public Component add(Component comp, int index) {
        this.contentPane.add(comp, index);
        if (!this.shown && this.programFrame != null) {
            this.programFrame.show();
            this.shown = true;
        }
        return comp;
    }

    public void add(Component comp, Object constraints) {
        this.contentPane.add(comp, constraints);
        if (!this.shown && this.programFrame != null) {
            this.programFrame.show();
            this.shown = true;
        }
    }

    public void add(Component comp, Object constraints, int index) {
        this.contentPane.add(comp, constraints, index);
        if (!this.shown && this.programFrame != null) {
            this.programFrame.show();
            this.shown = true;
        }
    }

    public void remove(int index) {
        this.contentPane.remove(index);
    }

    public void remove(Component comp) {
        this.contentPane.remove(comp);
    }

    public void removeAll() {
        this.contentPane.removeAll();
    }

    public void setBackground(Color color) {
        if (this.backgroundPane != null) {
            this.backgroundPane.setBackground(color);
        }
        super.setBackground(color);
    }

    public void validate() {
        if (this.contentPane != null) {
            this.contentPane.validate();
        }
        this.rootPane.validate();
        super.validate();
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
        if (this.programFrame != null) {
            this.programFrame.setMenuBar(menuBar);
        }
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public final void init() {
        boolean bl = this.isAppletMode = this.getParent() != null;
        if (!this.isAppletMode) {
            throw new ErrorException("Programs cannot make explicit calls to init()");
        }
        this.started = true;
        this.addBorders();
        this.initAppletFrame();
        this.validate();
        MediaTools.setCodeBase(this.getCodeBase());
        this.setVisible(true);
        this.startRun();
    }

    public final void start() {
        if (!this.isAppletMode) {
            this.start(null);
        }
    }

    public static void main(String[] args) {
        Hashtable ht = Program.createParameterTable(args);
        String className = (String)ht.get("code");
        Class mainClass = null;
        Program program = null;
        if (className == null) {
            className = Program.readMainClassFromCommandLine(Program.getCommandLine());
        }
        if (className != null) {
            if (className.endsWith(".class")) {
                className = className.substring(0, className.length() - 6);
            }
            className = className.replace('/', '.');
            try {
                mainClass = Class.forName(className);
            }
            catch (ClassNotFoundException var5_5) {
                // empty catch block
            }
        }
        if (mainClass != null) {
            try {
                Object obj3 = mainClass.newInstance();
                if (obj3 instanceof Program) {
                    program = (Program)obj3;
                    program.setStartupObject(null);
                } else {
                    className = (String)ht.get("program");
                    if (className == null) {
                        throw new ErrorException("Main class does not specify a program");
                    }
                    program = (Program)Class.forName(className).newInstance();
                    program.setStartupObject(obj3);
                }
            }
            catch (IllegalAccessException obj3) {
            }
            catch (InstantiationException obj3) {
            }
            catch (ClassNotFoundException obj3) {
                // empty catch block
            }
            if (program == null) {
                throw new ErrorException("You need to specify a static main method in your program if you want to run it as an application.");
            }
        }
        program.setParameterTable(ht);
        program.start();
    }

    protected Component getBorder(String side) {
        if (side.equalsIgnoreCase("North")) {
            return this.northBorder;
        }
        if (side.equalsIgnoreCase("South")) {
            return this.southBorder;
        }
        if (side.equalsIgnoreCase("East")) {
            return this.eastBorder;
        }
        if (side.equalsIgnoreCase("West")) {
            return this.westBorder;
        }
        throw new ErrorException("Illegal border specification - " + side);
    }

    protected String[] getArgumentArray() {
        return this.parameterTable == null ? null : (String[])this.parameterTable.get("ARGS");
    }

    protected Container getContentPane() {
        return this.contentPane;
    }

    protected Container getRootPane() {
        return this.rootPane;
    }

    protected Container getGlassPane() {
        return this.glassPane;
    }

    protected Component getBackgroundPane() {
        return this.backgroundPane;
    }

    protected boolean isStarted() {
        IOConsole console = this.getConsole();
        if (console == null) {
            return false;
        }
        if (console.getParent() == null) {
            return true;
        }
        Dimension size = console.getSize();
        if (console.isShowing() && size.width != 0 && size.height != 0) {
            return true;
        }
        return false;
    }

    protected void startHook() {
    }

    protected void endHook() {
    }

    protected void setAppletStub(AppletStub stub) {
        this.stub = stub;
        this.setStub(stub);
    }

    protected AppletStub getAppletStub() {
        return this.stub;
    }

    protected void setParameterTable(Hashtable ht) {
        this.parameterTable = ht;
    }

    protected Hashtable getParameterTable() {
        return this.parameterTable;
    }

    protected void setStartupObject(Object obj) {
        this.startupObject = obj;
    }

    protected Object getStartupObject() {
        return this.startupObject;
    }

    protected void start(String[] args) {
        if (this.parameterTable == null) {
            this.parameterTable = Program.createParameterTable(args);
        }
        if (this.getParent() == null) {
            this.initApplicationFrame();
        }
        this.started = true;
        this.addBorders();
        this.validate();
        this.programFrame.validate();
        this.setVisible(true);
        this.startRun();
    }

    protected static String getCommandLine() {
        switch (Platform.getPlatform()) {
            case 1:
            case 2: {
                return Program.getShellCommandLine();
            }
            case 3: {
                return Program.getDOSCommandLine();
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void startRun() {
        ProgramStartupListener listener = new ProgramStartupListener();
        this.rootPane.addComponentListener(listener);
        this.rootPane.validate();
        Program.pause(1000.0);
        ProgramStartupListener programStartupListener = listener;
        synchronized (programStartupListener) {
            while (!this.isStarted()) {
                try {
                    this.wait(300);
                }
                catch (InterruptedException var3_3) {
                    // empty catch block
                }
            }
        }
        this.rootPane.update(this.rootPane.getGraphics());
        this.startHook();
        this.runHook();
        this.endHook();
        if (this.contentPane.getComponentCount() == 0 && !this.isAppletMode) {
            System.exit(0);
        }
    }

    protected void runHook() {
        this.run();
    }

    protected static Hashtable createParameterTable(String[] args) {
        if (args == null) {
            return null;
        }
        Hashtable<String, String> ht = new Hashtable<String, String>();
        Vector<String> v = new Vector<String>();
        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            int equals = arg.indexOf(61);
            if (equals > 0) {
                String name = arg.substring(0, equals).toLowerCase();
                String value = arg.substring(equals + 1);
                ht.put(name, value);
            } else {
                v.addElement(arg);
            }
            ++i;
        }
        String[] newArgs = new String[v.size()];
        int i2 = 0;
        while (i2 < v.size()) {
            newArgs[i2] = (String)v.elementAt(i2);
            ++i2;
        }
        // ht.put("ARGS", (String)newArgs);
        return ht;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static String readMainClassFromCommandLine(String line) {
        if (line == null) {
            return null;
        }

        boolean jarFlag = false;
        StringTokenizer tokenizer = new StringTokenizer(line);

        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        do {
          String token = tokenizer.nextToken();
          if (token.endsWith("java"))
          {
              token = tokenizer.nextToken();
              if (!token.startsWith("-")) {
                  if (jarFlag == false) return token;
                  return Program.readMainClassFromManifest(token);
              }
              if (token.equals("-jar")) {
                  jarFlag = true;
                  break;
              }
              if (!token.equals("-cp") && !token.equals("-classpath")) continue;
              tokenizer.nextToken();
          }
        } while(true);
        
        return null;
    }

    private void initAppletFrame() {
        super.add("Center", this.rootPane);
        this.validate();
    }

    private void initApplicationFrame() {
        this.programFrame = this.createProgramFrame();
        ((ProgramAppletStub)this.stub).setFrame(this.programFrame);
        this.setFrameBounds(this.programFrame);
        this.programFrame.add("Center", this.rootPane);
        this.programFrame.addWindowListener(new ProgramWindowListener());
        if (this.contentPane.getComponentCount() != 0) {
            this.programFrame.show();
            this.shown = true;
        }
    }

    private void addBorders() {
        this.northBorder = this.createBorder("North");
        if (this.northBorder != null) {
            super.add("North", this.northBorder);
        }
        this.southBorder = this.createBorder("South");
        if (this.southBorder != null) {
            super.add("South", this.southBorder);
        }
        this.eastBorder = this.createBorder("East");
        if (this.eastBorder != null) {
            super.add("East", this.eastBorder);
        }
        this.westBorder = this.createBorder("West");
        if (this.westBorder != null) {
            super.add("West", this.westBorder);
        }
    }

    private void initContentPane() {
        this.contentPane = new ProgramContentPane();
        this.contentPane.setLayout(new BorderLayout());
    }

    private void initRootPane() {
        this.backgroundPane = new ProgramBackgroundPane();
        String colorName = this.getParameter("bgcolor");
        Color bgColor = colorName == null ? DEFAULT_BGCOLOR : this.decodeColor(colorName);
        this.backgroundPane.setBackground(bgColor);
        this.glassPane = new ProgramGlassPane();
        this.rootPane = new ProgramRootPane(this, this.backgroundPane, this.contentPane, this.glassPane);
    }

    private void setFrameBounds(Frame frame) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = this.decodeSizeParameter("WIDTH", 754, size.width);
        int height = this.decodeSizeParameter("HEIGHT", 492, size.height);
        int x = this.decodeSizeParameter("X", width >= size.width ? 0 : 16, size.width);
        int y = this.decodeSizeParameter("Y", height >= size.height ? 0 : 40, size.height);
        frame.setBounds(x, y, width, height);
    }

    private Color decodeColor(String name) {
        if (name.equalsIgnoreCase("black")) {
            return Color.black;
        }
        if (name.equalsIgnoreCase("blue")) {
            return Color.blue;
        }
        if (name.equalsIgnoreCase("cyan")) {
            return Color.cyan;
        }
        if (name.equalsIgnoreCase("darkGray")) {
            return Color.darkGray;
        }
        if (name.equalsIgnoreCase("gray")) {
            return Color.gray;
        }
        if (name.equalsIgnoreCase("green")) {
            return Color.green;
        }
        if (name.equalsIgnoreCase("lightGray")) {
            return Color.lightGray;
        }
        if (name.equalsIgnoreCase("magenta")) {
            return Color.magenta;
        }
        if (name.equalsIgnoreCase("orange")) {
            return Color.orange;
        }
        if (name.equalsIgnoreCase("pink")) {
            return Color.pink;
        }
        if (name.equalsIgnoreCase("red")) {
            return Color.red;
        }
        if (name.equalsIgnoreCase("white")) {
            return Color.white;
        }
        if (name.equalsIgnoreCase("yellow")) {
            return Color.yellow;
        }
        return Color.decode(name);
    }

    private int decodeSizeParameter(String name, int value, int max) {
        String str = this.getParameter(name);
        if (str == null) {
            return value;
        }
        if (str.equals("*")) {
            str = "100%";
        }
        if (str.endsWith("%")) {
            int percent = Integer.parseInt(str.substring(0, str.length() - 1));
            return (int)Math.round((double)percent / 100.0 * (double)max);
        }
        return Integer.parseInt(str);
    }

    private static String readMainClassFromManifest(String jarName) {
        try {
            ZipFile jarFile = new ZipFile(jarName);
            ZipEntry entry = jarFile.getEntry("META-INF/MANIFEST.MF");
            if (entry == null) {
                return null;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)));
            String line = rd.readLine();
            while (line != null) {
                if (line.startsWith("Main-Class:")) {
                    return line.substring("Main-Class:".length()).trim();
                }
                line = rd.readLine();
            }
            return null;
        }
        catch (IOException ex) {
            return null;
        }
    }

    private static String getShellCommandLine() {
        try {
            String option = Platform.isMac() ? "command" : "args";
            String[] argv = new String[]{"bash", "-c", "ps -p $PPID -o " + option};
            Process p = Runtime.getRuntime().exec(argv);
            p.waitFor();
            if (p.getErrorStream().read() != -1) {
                return null;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String header = rd.readLine();
            return rd.readLine();
        }
        catch (Exception ex) {
            return null;
        }
    }

    private static String getDOSCommandLine() {
        return null;
    }
}
