/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.AWTDialogModel;
import acm.io.DialogModel;
import acm.io.IOConsole;
import acm.io.IOModel;
import acm.util.CancelledException;
import acm.util.ErrorException;
import acm.util.Platform;
import java.awt.Component;
import java.io.BufferedReader;
import java.lang.reflect.Constructor;

public class IODialog
implements IOModel {
    private boolean exceptionOnError;
    private boolean allowCancel;
    private DialogModel model;
    private Component owner;
    private IOConsole console;
    private String outputLine;

    public IODialog() {
        this(null);
    }

    public IODialog(Component owner) {
        this.owner = owner;
        this.model = this.createModel();
        this.outputLine = "";
        this.exceptionOnError = false;
        this.allowCancel = false;
    }

    public void print(String value) {
        this.outputLine = String.valueOf(this.outputLine) + value;
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
        this.model.popupMessage(this.outputLine);
        this.outputLine = "";
    }

    public void println(String value) {
        this.print(value);
        this.println();
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
        this.model.popupErrorMessage(msg);
    }

    public final String readLine() {
        return this.readLine(null);
    }

    public String readLine(String prompt) {
        return this.readDialogLine(prompt, "Illegal input line");
    }

    public final int readInt() {
        return this.readInt(null);
    }

    public int readInt(String prompt) {
        String line = this.readDialogLine(prompt, "Illegal integer format");
        return Integer.parseInt(line);
    }

    public final double readDouble() {
        return this.readDouble(null);
    }

    public double readDouble(String prompt) {
      String line = this.readDialogLine(prompt, "Illegal numeric format");
      return Double.valueOf(line);
    }

    public final boolean readBoolean() {
        return this.readBoolean(null);
    }

    public final boolean readBoolean(String prompt) {
        return this.readBoolean(prompt, "true", "false");
    }

    public boolean readBoolean(String prompt, String trueLabel, String falseLabel) {
        Boolean choice;
        if (this.console != null && this.console.getInputScript() != null) {
            return this.console.readBoolean(prompt, trueLabel, falseLabel);
        }
        prompt = prompt == null ? this.outputLine : String.valueOf(this.outputLine) + prompt;
        this.outputLine = "";
        while ((choice = this.model.popupBooleanInputDialog(prompt, trueLabel, falseLabel, this.allowCancel)) == null) {
            if (!this.allowCancel) continue;
            throw new CancelledException();
        }
        return choice;
    }

    public void setExceptionOnError(boolean flag) {
        this.exceptionOnError = flag;
    }

    public boolean getExceptionOnError() {
        return this.exceptionOnError;
    }

    public void setAllowCancel(boolean flag) {
        this.allowCancel = flag;
    }

    public boolean getAllowCancel() {
        return this.allowCancel;
    }

    public void setAssociatedConsole(IOConsole console) {
        this.console = console;
    }

    public IOConsole getAssociatedConsole() {
        return this.console;
    }

    protected DialogModel createModel() {
        if (Platform.isSwingAvailable()) {
            try {
                Class swingDialogModelClass = Class.forName("acm.io.SwingDialogModel");
                Class[] types = new Class[]{Class.forName("java.awt.Component")};
                Object[] args = new Object[]{this.owner};
                Constructor con = swingDialogModelClass.getConstructor(types);
                return (DialogModel)con.newInstance(args);
            }
            catch (Exception ex) {
                return new AWTDialogModel(this.owner);
            }
        }
        return new AWTDialogModel(this.owner);
    }

    private String readDialogLine(String prompt, String message) {
        String line;
        if (this.console != null && this.console.getInputScript() != null) {
            return this.console.readLine(prompt);
        }
        prompt = prompt == null ? this.outputLine : String.valueOf(this.outputLine) + prompt;
        this.outputLine = "";
        while ((line = this.model.popupLineInputDialog(prompt, this.allowCancel)) == null) {
            if (!this.allowCancel) continue;
            throw new CancelledException();
        }
        return line;
    }

    private void signalError(String msg) {
        if (this.exceptionOnError) {
            throw new ErrorException(msg);
        }
        this.model.popupErrorMessage(msg);
    }
}
