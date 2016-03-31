/*
 * Decompiled with CFR 0_114.
 */
package acm.io;

import acm.io.IOConsole;

class ConsoleTest {
    ConsoleTest() {
    }

    public void test(IOConsole console) {
        console.println("IOConsole class test");
        int n = console.readInt("Enter an integer: ");
        console.println("That integer was " + n);
        double d = console.readDouble("Enter a real number: ");
        console.println("That number was " + d);
        boolean b = console.readBoolean("Enter a boolean value: ");
        console.println("That value was " + b);
        String line = console.readLine("Enter a line: ");
        console.println("That line was \"" + line + "\"");
    }
}

