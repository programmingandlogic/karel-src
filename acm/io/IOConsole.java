/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.AWTConsoleModel;
import acm.io.ConsoleFocusListener;
import acm.io.ConsoleMenuBar;
import acm.io.ConsoleModel;
import acm.io.ConsoleReader;
import acm.io.ConsoleTest;
import acm.io.ConsoleWriter;
import acm.io.IODialog;
import acm.io.IOModel;
import acm.io.SystemConsole;
import acm.util.ErrorException;
import acm.util.Platform;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.PrintJob;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class IOConsole
extends Container
implements IOModel {
    public static final IOConsole SYSTEM_CONSOLE = new SystemConsole();
    protected static final Font DEFAULT_FONT = new Font("Monospaced", 0, 12);
    protected static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private ConsoleModel model;
    private Component consolePane;
    private Component textPane;
    private boolean exceptionOnError;
    private BufferedReader rd;
    private PrintWriter wr;
    private MenuBar menuBar;
    private boolean menuBarInitialized;
    private File file;
    private Color inputColor;
    private int inputStyle;
    private Color errorColor;
    private int errorStyle;

    public IOConsole() {
        this.model = this.createConsoleModel();
        this.setLayout(new BorderLayout());
        this.consolePane = this.model.getConsolePane();
        this.textPane = this.model.getTextPane();
        if (this.consolePane != null) {
            this.textPane.addFocusListener(new ConsoleFocusListener(this));
            this.add("Center", this.consolePane);
        }
        this.rd = new BufferedReader(new ConsoleReader(this.model));
        this.wr = new PrintWriter(new ConsoleWriter(this.model));
        this.exceptionOnError = false;
    }

    public void clear() {
        this.model.clear();
    }

    public void print(String value) {
        this.wr.print(value);
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
        this.wr.println();
    }

    public void println(String value) {
        this.wr.println(value);
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
        this.model.print(String.valueOf(msg) + "\n", 2);
    }

    public final String readLine() {
        return this.readLine(null);
    }

    public String readLine(String prompt) {
        if (prompt != null) {
            this.print(prompt);
        }
        if (this.textPane != null) {
            this.textPane.requestFocus();
        }
        try {
            String str = this.rd.readLine();
            return str;
        }
        catch (IOException ex) {
            throw new ErrorException(ex);
        }
    }

    public final int readInt() {
        return this.readInt(null);
    }

    public final int readInt(String prompt) {
      String line = this.readLine(prompt);
      return Integer.parseInt(line);
    }

    public final double readDouble() {
        return this.readDouble(null);
    }

    public final double readDouble(String prompt) {
      String line = this.readLine(prompt);
      return Double.valueOf(line);
    }

    public final boolean readBoolean() {
        return this.readBoolean(null);
    }

    public final boolean readBoolean(String prompt) {
        return this.readBoolean(prompt, "true", "false");
    }

    public boolean readBoolean(String prompt, String trueLabel, String falseLabel) {
        do {
            String line;
            if ((line = this.readLine(prompt)) == null) {
                throw new ErrorException("End of file encountered");
            }
            if (line.equalsIgnoreCase(trueLabel)) {
                return true;
            }
            if (line.equalsIgnoreCase(falseLabel)) {
                return false;
            }
            if (this.exceptionOnError) {
                throw new ErrorException("Illegal boolean format");
            }
            this.showErrorMessage("Illegal boolean format");
            if (prompt != null) continue;
            prompt = "Retry: ";
        } while (true);
    }

    public BufferedReader getReader() {
        return this.rd;
    }

    public PrintWriter getWriter() {
        return this.wr;
    }

    public void setExceptionOnError(boolean flag) {
        this.exceptionOnError = flag;
    }

    public boolean getExceptionOnError() {
        return this.exceptionOnError;
    }

    public void setInputStyle(int style) {
        this.inputStyle = style;
        this.model.setInputStyle(style);
    }

    public int getInputStyle() {
        return this.inputStyle;
    }

    public void setInputColor(Color color) {
        this.inputColor = color;
        this.model.setInputColor(color);
    }

    public Color getInputColor() {
        return this.inputColor;
    }

    public void setErrorStyle(int style) {
        this.errorStyle = style;
        this.model.setErrorStyle(style);
    }

    public int getErrorStyle() {
        return this.errorStyle;
    }

    public void setErrorColor(Color color) {
        this.errorColor = color;
        this.model.setErrorColor(color);
    }

    public Color getErrorColor() {
        return this.errorColor;
    }

    public MenuBar getMenuBar() {
        if (!this.menuBarInitialized) {
            this.menuBar = this.createMenuBar();
            this.menuBarInitialized = true;
        }
        return this.menuBar;
    }

    public void setFont(Font font) {
        if (this.textPane != null) {
            this.textPane.setFont(font);
        }
        super.setFont(font);
    }

    public void setForeground(Color color) {
        if (this.textPane != null) {
            this.textPane.setForeground(color);
        }
        super.setForeground(color);
    }

    public void setInputScript(BufferedReader rd) {
        this.model.setInputScript(rd);
    }

    public BufferedReader getInputScript() {
        return this.model.getInputScript();
    }

    protected MenuBar createMenuBar() {
        return new ConsoleMenuBar(this);
    }

    public void save(Writer wr) {
        try {
            wr.write(this.model.getText());
        }
        catch (IOException ex) {
            throw new ErrorException(ex);
        }
    }

    public void print(PrintJob pj) {
        this.model.print(pj);
    }

    protected ConsoleModel createConsoleModel() {
        if (Platform.isSwingAvailable()) {
            try {
                return (ConsoleModel)Class.forName("acm.io.SwingConsoleModel").newInstance();
            }
            catch (Exception ex) {
                return new AWTConsoleModel();
            }
        }
        return new AWTConsoleModel();
    }

    protected void cut() {
        this.model.cut();
    }

    protected void copy() {
        this.model.copy();
    }

    protected void paste() {
        this.model.paste();
    }

    protected void selectAll() {
        this.model.selectAll();
    }

    protected void save() {
        FileWriter wr = null;
        while (wr == null) {
            try {
                if (this.file == null) {
                    Frame frame = Platform.getEnclosingFrame(this);
                    if (frame == null) {
                        return;
                    }
                    String dir = System.getProperty("user.dir");
                    FileDialog dialog = new FileDialog(frame, "Save Console As", 1);
                    dialog.setDirectory(dir);
                    dialog.setVisible(true);
                    String filename = dialog.getFile();
                    if (filename == null) {
                        return;
                    }
                    this.file = new File(new File(dir), filename);
                }
                wr = new FileWriter(this.file);
                this.save(wr);
                wr.close();
                Platform.setFileTypeAndCreator(this.file, "TEXT", "ttxt");
                continue;
            }
            catch (IOException ex) {
                IODialog dialog = new IODialog(this);
                dialog.showErrorMessage(ex.getMessage());
            }
        }
    }

    protected void saveAs() {
        this.file = null;
        this.save();
    }

    protected Component getConsolePane() {
        return this.consolePane;
    }

    protected Component getTextPane() {
        return this.textPane;
    }

    public static void test() {
        Frame frame = new Frame("IOConsole Test");
        IOConsole console = new IOConsole();
        frame.setLayout(new BorderLayout());
        frame.add("Center", console);
        frame.setBounds(50, 50, 400, 170);
        frame.show();
        new ConsoleTest().test(console);
    }
}
