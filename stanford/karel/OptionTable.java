/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.util.Hashtable;
import java.util.StringTokenizer;

class OptionTable
extends Hashtable {
    private static final int INITIAL_STATE = 0;
    private static final int KEY_SEEN = 1;
    private static final int COLON_SEEN = 2;
    private static final int VALUE_SEEN = 3;

    public OptionTable() {
    }

    public OptionTable(String options) {
        this();
        this.parseOptions(options);
    }

    public void parseOptions(String options) {
        OptionTable.parseOptions(options, this);
    }

    public boolean isSpecified(String key) {
        return this.containsKey(key);
    }

    public String getOption(String key) {
        return this.getOption(key, null);
    }

    public String getOption(String key, String defValue) {
        String value = (String)this.get(key.toLowerCase());
        return value == null || value.equals("") ? defValue : value;
    }

    public int getIntOption(String key) {
        return this.getIntOption(key, 0);
    }

    public int getIntOption(String key, int defValue) {
        String value = this.getOption(key, null);
        if (value == null || value.equals("")) {
            return defValue;
        }
        return Integer.decode(value);
    }

    public double getDoubleOption(String key) {
        return this.getDoubleOption(key, 0.0);
    }

    public double getDoubleOption(String key, double defValue) {
        String value = this.getOption(key, null);
        if (value == null || value.equals("")) {
            return defValue;
        }
        return Double.valueOf(value);
    }

    public boolean getFlagOption(String key) {
        return this.getFlagOption(key, false);
    }

    public boolean getFlagOption(String key, boolean def) {
        String value = (String)this.get(key.toLowerCase());
        if (value == null) {
            return def;
        }
        if ((value = value.toLowerCase()).equals("") || value.equals("true") || value.equals("t") || value.equals("on")) {
            return true;
        }
        if (value.equals("false") || value.equals("f") || value.equals("off")) {
            return false;
        }
        throw new IllegalArgumentException("parseOptions: Illegal flag value");
    }

    private static void parseOptions(String options, Hashtable table) {
        StringTokenizer scanner = new StringTokenizer(String.valueOf(options) + "/", "/:", true);
        String key = null;
        String value = null;
        int state = 0;
        while (scanner.hasMoreTokens()) {
            String token = scanner.nextToken();
            switch (state) {
                case 0: {
                    if (token.equals("/")) break;
                    key = token.toLowerCase();
                    value = "";
                    state = 1;
                    break;
                }
                case 1: {
                    if (token.equals("/")) {
                        table.put(key, value);
                        state = 0;
                        break;
                    }
                    if (!token.equals(":")) break;
                    state = 2;
                    break;
                }
                case 2: {
                    value = token;
                    state = 3;
                    break;
                }
                case 3: {
                    if (token.equals("/")) {
                        table.put(key, value);
                        state = 0;
                        break;
                    }
                    throw new IllegalArgumentException("parseOptions: Illegal option string");
                }
            }
        }
    }
}

