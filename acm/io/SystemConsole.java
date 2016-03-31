/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.ConsoleModel;
import acm.io.IOConsole;
import acm.io.SystemConsoleModel;

class SystemConsole
extends IOConsole {
    SystemConsole() {
    }

    protected ConsoleModel createConsoleModel() {
        return new SystemConsoleModel();
    }
}

