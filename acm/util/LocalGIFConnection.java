/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class LocalGIFConnection
extends URLConnection {
    private InputStream in;

    public LocalGIFConnection(InputStream in, URL dummy) {
        super(dummy);
        this.in = in;
        this.setUseCaches(false);
    }

    public void connect() throws IOException {
    }

    public String getContentType() {
        return "image/gif";
    }

    public InputStream getInputStream() throws IOException {
        return this.in;
    }
}

