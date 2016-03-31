/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import acm.util.ErrorException;
import acm.util.HexInputStream;
import acm.util.LocalGIFConnection;
import acm.util.NullAudioClip;
import acm.util.SunAudioClip;
import acm.util.TrackerComponent;
import java.applet.AudioClip;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class MediaTools {
    public static final String DEFAULT_IMAGE_PATH = ".:images";
    public static final String DEFAULT_AUDIO_PATH = ".:sounds";
    private static URL codeBase = null;
    private static Hashtable imageTable = new Hashtable();
    private static Hashtable audioClipTable = new Hashtable();
    private static final Class RESOURCE_CLASS = new MediaTools().getClass();

    private MediaTools() {
    }

    public static Image loadImage(String name) {
        return MediaTools.loadImage(name, ".:images");
    }

    public static Image loadImage(String name, String path) {
        Image image = (Image)imageTable.get(name);
        if (image != null) {
            return image;
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        StringTokenizer tokenizer = new StringTokenizer(path, ":");
        while (image == null && tokenizer.hasMoreTokens()) {
            String prefix = tokenizer.nextToken();
            prefix = prefix.equals(".") ? "" : String.valueOf(prefix) + "/";
            URL url = null;
            try {
                url = RESOURCE_CLASS.getResource("/" + prefix + name);
            }
            catch (Exception var7_8) {
                // empty catch block
            }
            if (url == null) {
                try {
                    if (codeBase != null) {
                        url = new URL(codeBase, String.valueOf(prefix) + name);
                    }
                }
                catch (MalformedURLException var7_9) {
                    // empty catch block
                }
            }
            if (url == null) {
                try {
                    if (!new File(String.valueOf(prefix) + name).canRead()) continue;
                    image = toolkit.getImage(String.valueOf(prefix) + name);
                }
                catch (SecurityException ex) {}
                continue;
            }
            try {
                URLConnection connection = url.openConnection();
                if (connection.getContentLength() <= 0) continue;
                Object content = connection.getContent();
                if (content instanceof ImageProducer) {
                    image = toolkit.createImage((ImageProducer)content);
                    continue;
                }
                if (content == null) continue;
                image = toolkit.getImage(url);
                continue;
            }
            catch (IOException connection) {
                // empty catch block
            }
        }
        if (image == null) {
            throw new ErrorException("Cannot find an image named " + name);
        }
        MediaTools.loadImage(image);
        imageTable.put(name, image);
        return image;
    }

    public static Image loadImage(Image image) {
        MediaTracker tracker = new MediaTracker(new TrackerComponent());
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        }
        catch (InterruptedException ex) {
            throw new ErrorException("Image loading process interrupted");
        }
        return image;
    }

    public static void defineImage(String name, Image image) {
        imageTable.put(name, image);
    }

    public static void flushImage(String name) {
        imageTable.remove(name);
    }

    public static Image createImage(int[] pixels, int width, int height) {
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixels, 0, width));
    }

    public static Image createImage(InputStream in) {
        try {
            LocalGIFConnection connection = new LocalGIFConnection(in, MediaTools.getDummyURL());
            Object content = connection.getContent();
            Image image = Toolkit.getDefaultToolkit().createImage((ImageProducer)content);
            return image;
        }
        catch (Exception ex) {
            throw new ErrorException("Exception: " + ex);
        }
    }

    public static Image createImage(String[] hexData) {
        return MediaTools.createImage(new HexInputStream(hexData));
    }

    public static Component getImageObserver() {
        return new TrackerComponent();
    }

    public static AudioClip loadAudioClip(String name) {
        return MediaTools.loadAudioClip(name, ".:sounds");
    }

    public static AudioClip loadAudioClip(String name, String path) {
        AudioClip clip = (AudioClip)audioClipTable.get(name);
        if (clip != null) {
            return clip;
        }
        StringTokenizer tokenizer = new StringTokenizer(path, ":");
        while (clip == null && tokenizer.hasMoreTokens()) {
            String prefix = tokenizer.nextToken();
            prefix = prefix.equals(".") ? "" : String.valueOf(prefix) + "/";
            URL url = null;
            try {
                url = RESOURCE_CLASS.getResource("/" + prefix + name);
            }
            catch (Exception var6_7) {
                // empty catch block
            }
            if (url == null) {
                try {
                    if (codeBase != null) {
                        url = new URL(codeBase, String.valueOf(prefix) + name);
                    }
                }
                catch (MalformedURLException var6_8) {
                    // empty catch block
                }
            }
            if (url == null) {
                try {
                    File file = new File(String.valueOf(prefix) + name);
                    if (!file.canRead()) continue;
                    clip = MediaTools.createAudioClip(new FileInputStream(file));
                }
                catch (Exception ex) {}
                continue;
            }
            try {
                URLConnection connection = url.openConnection();
                if (connection.getContentLength() <= 0) continue;
                Object content = connection.getContent();
                if (content instanceof AudioClip) {
                    clip = (AudioClip)content;
                    continue;
                }
                if (!(content instanceof InputStream)) continue;
                clip = MediaTools.createAudioClip((InputStream)content);
                continue;
            }
            catch (IOException connection) {
                // empty catch block
            }
        }
        if (clip == null) {
            throw new ErrorException("Cannot find an audio clip named " + name);
        }
        audioClipTable.put(name, clip);
        return clip;
    }

    public static void defineAudioClip(String name, AudioClip clip) {
        audioClipTable.put(name, clip);
    }

    public static void flushAudioClip(String name) {
        audioClipTable.remove(name);
    }

    public static AudioClip createAudioClip(InputStream in) {
        try {
            return new SunAudioClip(in);
        }
        catch (Exception ex) {
            return new NullAudioClip();
        }
    }

    public static AudioClip createAudioClip(String[] hexData) {
        return MediaTools.createAudioClip(new HexInputStream(hexData));
    }

    public static InputStream getHexInputStream(String[] hexData) {
        return new HexInputStream(hexData);
    }

    public static void setCodeBase(URL url) {
        codeBase = url;
    }

    public static URL getCodeBase() {
        return codeBase;
    }

    private static URL getDummyURL() {
        try {
            if (codeBase != null) {
                return codeBase;
            }
            return new URL("verbatim://dummy");
        }
        catch (Exception ex) {
            return null;
        }
    }
}

