/*
 * Decompiled with CFR 0_114.
 */
package acm.program;

import acm.program.Program;
import acm.util.ErrorException;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

class ProgramAppletStub
implements AppletContext,
AppletStub {
    private Applet applet;
    private Program program;
    private Frame frame;

    public ProgramAppletStub(Program program) {
        this.program = program;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public boolean isActive() {
        return true;
    }

    public URL getDocumentBase() {
        return this.getCodeBase();
    }

    public URL getCodeBase() {
        try {
            return new URL("file:" + this.getCanonicalPath("."));
        }
        catch (MalformedURLException ex) {
            throw new ErrorException("Error: Illegal document base URL");
        }
    }

    public String getParameter(String name) {
        return null;
    }

    public AppletContext getAppletContext() {
        return this;
    }

    public void appletResize(int width, int height) {
        this.frame.setSize(width, height);
    }

    public AudioClip getAudioClip(URL url) {
        AudioClip clip = null;
        if (clip == null) {
            clip = this.getNewAudioClip(url);
        }
        return clip;
    }

    public Image getImage(URL url) {
        try {
            Object content = url.getContent();
            if (content instanceof ImageProducer) {
                return this.program.createImage((ImageProducer)content);
            }
        }
        catch (IOException content) {
            // empty catch block
        }
        return null;
    }

    public Applet getApplet(String name) {
        return null;
    }

    public Enumeration getApplets() {
        return new Vector().elements();
    }

    public void showDocument(URL url) {
        if (this.applet != null) {
            this.applet.getAppletContext().showDocument(url);
        }
    }

    public void showDocument(URL url, String target) {
        if (this.applet != null) {
            this.applet.getAppletContext().showDocument(url, target);
        }
    }

    public void showStatus(String status) {
        if (this.applet == null) {
            System.out.println(status);
        } else {
            this.applet.showStatus(status);
        }
    }

    public void setStream(String key, InputStream stream) {
        throw new ErrorException("setStream: unimplemented operation");
    }

    public InputStream getStream(String key) {
        throw new ErrorException("getStream: unimplemented operation");
    }

    public Iterator getStreamKeys() {
        throw new ErrorException("getStreamKeys: unimplemented operation");
    }

    private String getCanonicalPath(String start) {
        int sp;
        String path = new File(start).getAbsolutePath();
        while ((sp = path.indexOf(32)) != -1) {
            path = String.valueOf(path.substring(0, sp)) + "%20" + path.substring(sp + 1);
        }
        return path;
    }

    private AudioClip getNewAudioClip(URL url) {
        ProgramAppletStub programAppletStub = this;
        synchronized (programAppletStub) {
            try {
                Class[] argTypes = new Class[1];
                Object[] args = new Object[]{url};
                Class type = Class.forName("java.applet.Applet");
                argTypes[0] = Class.forName("java.net.URL");
                Method fn = type.getMethod("newAudioClip", argTypes);
                return (AudioClip)fn.invoke(null, args);
            }
            catch (Exception ex) {
                return null;
            }
        }
    }
}

