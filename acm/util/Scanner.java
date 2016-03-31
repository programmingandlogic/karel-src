/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import acm.util.ErrorException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Scanner {
    private static final String RADIX_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private Reader source;
    private String saved;
    private String delimiter;
    private int radix;

    public Scanner(File file) {
        try {
            this.initScanner(new FileReader(file));
        }
        catch (IOException ex) {
            throw new ErrorException(ex);
        }
    }

    public Scanner(InputStream in) {
        this.initScanner(new InputStreamReader(in));
    }

    public Scanner(String str) {
        this.initScanner(new StringReader(str));
    }

    public Scanner(Reader rd) {
        this.initScanner(rd);
    }

    public boolean hasNext() {
        this.skipDelimiters();
        int ch = this.read();
        if (ch == -1) {
            return false;
        }
        this.save(ch);
        return true;
    }

    public boolean hasNextBoolean() {
        this.skipDelimiters();
        String token = this.scanBoolean();
        if (token != null) {
            this.save(token);
        }
        if (token != null) {
            return true;
        }
        return false;
    }

    public boolean hasNextDouble() {
        this.skipDelimiters();
        String token = this.scanDouble();
        if (token != null) {
            this.save(token);
        }
        if (token != null) {
            return true;
        }
        return false;
    }

    public boolean hasNextInt() {
        this.skipDelimiters();
        String token = this.scanInt();
        if (token != null) {
            this.save(token);
        }
        if (token != null) {
            return true;
        }
        return false;
    }

    public Object next() {
        return this.nextToken();
    }

    public String nextToken() {
        int ch = 0;
        String token = "";
        this.skipDelimiters();
        while (!this.isDelimiter(ch = this.read())) {
            token = String.valueOf(token) + (char)ch;
        }
        this.save(ch);
        return token;
    }

    public boolean nextBoolean() {
        String token = this.scanBoolean();
        if (token == null) {
            throw new ErrorException("nextBoolean: No boolean value found");
        }
        return token.equalsIgnoreCase("true");
    }

    public double nextDouble() {
        String token = this.scanDouble();
        if (token == null) {
            throw new ErrorException("nextDouble: No double value found");
        }
        if (token.startsWith("+")) {
            token = token.substring(1);
        }
        return Double.valueOf(token);
    }

    public int nextInt() {
        String token = this.scanInt();
        if (token == null) {
            throw new ErrorException("nextInt: No integer value found");
        }
        if (token.startsWith("+")) {
            token = token.substring(1);
        }
        return Integer.parseInt(token, this.radix);
    }

    public String nextLine() {
        int ch = 0;
        String token = "";
        while ((ch = this.read()) != -1 && ch != 13 && ch != 10) {
            token = String.valueOf(token) + (char)ch;
        }
        if (ch == 13 && (ch = this.read()) != 10) {
            this.save(ch);
        }
        return token;
    }

    public void remove() {
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String pattern) {
        this.checkDelimiterPattern(pattern);
        this.delimiter = pattern;
    }

    public void useDelimiter(String pattern) {
        this.setDelimiter(pattern);
    }

    public int getRadix() {
        return this.radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public void useRadix(int radix) {
        this.setRadix(radix);
    }

    private void initScanner(Reader source) {
        this.source = source;
        this.saved = "";
        this.delimiter = null;
        this.radix = 10;
    }

    private int read() {
        int ch = 0;
        if (this.saved.length() == 0) {
            try {
                ch = this.source.read();
            }
            catch (IOException ex) {
                throw new ErrorException(ex);
            }
        } else {
            ch = this.saved.charAt(0);
            this.saved = this.saved.substring(1);
        }
        return ch;
    }

    private void save(int ch) {
        if (ch != -1) {
            this.saved = String.valueOf((char)ch) + this.saved;
        }
    }

    private void save(String str) {
        this.saved = String.valueOf(str) + this.saved;
    }

    private void checkDelimiterPattern(String pattern) {
        if (pattern == null) {
            return;
        }
        if (!pattern.startsWith("[") || !pattern.endsWith("]")) {
            throw new ErrorException("The only legal delimiter patterns for this implementation of the Scanner abstraction are a list of characters or character ranges enclosed in brackets.");
        }
    }

    private void skipDelimiters() {
        int ch = 0;
        while ((ch = this.read()) != -1 && this.isDelimiter(ch)) {
        }
        this.save(ch);
    }

    private boolean isDelimiter(int ch) {
        return this.isDelimiter(ch, this.delimiter);
    }

    private boolean isDelimiter(int ch, String pattern) {
        if (ch == -1) {
            return true;
        }
        if (pattern == null) {
            return Character.isWhitespace((char)ch);
        }
        boolean matched = false;
        boolean negate = false;
        int startChar = 0;
        int i = 1;
        while (!matched && i < pattern.length() - 1) {
            char pch = pattern.charAt(i);
            if (pch == '^' && i == 1) {
                negate = true;
            } else if (pch == '-' && startChar > 0 && i != pattern.length() - 2) {
                matched = startChar <= ch && ch <= pattern.charAt(++i);
                startChar = 0;
            } else {
                matched = ch == pch;
                startChar = pch;
            }
            ++i;
        }
        if (matched != negate) {
            return true;
        }
        return false;
    }

    private String scanBoolean() {
        String token = "";
        String match = "";
        int ch = this.read();
        this.save(ch);
        switch (ch) {
            case 84: 
            case 116: {
                match = "true";
                break;
            }
            case 70: 
            case 102: {
                match = "false";
                break;
            }
            default: {
                return null;
            }
        }
        int i = 0;
        while (i < match.length()) {
            ch = this.read();
            if (ch == -1 || Character.toLowerCase((char)ch) != match.charAt(i)) {
                this.save(ch);
                this.save(token);
                return null;
            }
            token = String.valueOf(token) + (char)ch;
            ++i;
        }
        return token;
    }

    private String scanInt() {
        int ch = 0;
        String token = "";
        boolean valid = false;
        while ((ch = this.read()) != -1) {
            if ((ch == 45 || ch == 43) && token.length() == 0) {
                token = String.valueOf(token) + (char)ch;
                continue;
            }
            if (!this.isLegalDigit(ch, this.radix)) break;
            token = String.valueOf(token) + (char)ch;
            valid = true;
        }
        this.save(ch);
        if (valid) {
            return token;
        }
        this.save(token);
        return null;
    }

    private String scanDouble() {
        int ch = 0;
        String token = "";
        String exp = "";
        boolean valid = false;
        boolean dotSeen = false;
        while ((ch = this.read()) != -1) {
            if ((ch == 45 || ch == 43) && token.length() == 0) {
                token = String.valueOf(token) + (char)ch;
                continue;
            }
            if (ch == 46 && !dotSeen) {
                token = String.valueOf(token) + (char)ch;
                dotSeen = true;
                continue;
            }
            if (!Character.isDigit((char)ch)) break;
            token = String.valueOf(token) + (char)ch;
            valid = true;
        }
        if (!valid) {
            this.save(ch);
            this.save(token);
            return null;
        }
        if (ch != 101 && ch != 69) {
            this.save(ch);
            return token;
        }
        exp = "" + (char)ch;
        valid = false;
        while ((ch = this.read()) != -1) {
            if ((ch == 45 || ch == 43) && exp.length() == 1) {
                exp = String.valueOf(exp) + (char)ch;
                continue;
            }
            if (!Character.isDigit((char)ch)) break;
            exp = String.valueOf(exp) + (char)ch;
            valid = true;
        }
        this.save(ch);
        if (valid) {
            token = String.valueOf(token) + exp;
        } else {
            this.save(exp);
        }
        return token;
    }

    private boolean isLegalDigit(int ch, int radix) {
        if (ch == -1) {
            return false;
        }
        int index = "0123456789abcdefghijklmnopqrstuvwxyz".indexOf(Character.toLowerCase((char)ch));
        if (index >= 0 && index < radix) {
            return true;
        }
        return false;
    }
}

