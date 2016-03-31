/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Platform {
    public static final int UNKNOWN = 0;
    public static final int MAC = 1;
    public static final int UNIX = 2;
    public static final int WINDOWS = 3;
    private static int platform = -1;
    private static boolean isSwingAvailable;
    private static boolean swingChecked;
    private static boolean areCollectionsAvailable;
    private static boolean collectionsChecked;
    private static boolean isSunAudioAvailable;
    private static boolean sunAudioChecked;
    private static boolean isJMFAvailable;
    private static boolean jmfChecked;

    public static int getPlatform() {
        if (platform != -1) {
            return platform;
        }
        String name = System.getProperty("os.name", "").toLowerCase();
        if (name.startsWith("mac")) {
            platform = 1;
            return 1;
        }
        if (name.startsWith("windows")) {
            platform = 3;
            return 3;
        }
        if (name.startsWith("microsoft")) {
            platform = 3;
            return 3;
        }
        if (name.startsWith("ms")) {
            platform = 3;
            return 3;
        }
        if (name.startsWith("unix")) {
            platform = 2;
            return 2;
        }
        if (name.startsWith("linux")) {
            platform = 2;
            return 2;
        }
        platform = 0;
        return 0;
    }

    public static boolean isMac() {
        if (Platform.getPlatform() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isWindows() {
        if (Platform.getPlatform() == 3) {
            return true;
        }
        return false;
    }

    public static boolean isUnix() {
        if (Platform.getPlatform() == 2) {
            return true;
        }
        return false;
    }

    public static void setFileTypeAndCreator(String filename, String type, String creator) {
        if (!Platform.isMac()) {
            return;
        }
        try {
            Platform.setFileTypeAndCreator(new File(filename), type, creator);
        }
        catch (Exception var3_3) {
            // empty catch block
        }
    }

    public static void setFileTypeAndCreator(File file, String type, String creator) {
        if (!Platform.isMac()) {
            return;
        }
        try {
            Class mrjOSTypeClass = Class.forName("com.apple.mrj.MRJOSType");
            Class mrjFileUtilsClass = Class.forName("com.apple.mrj.MRJFileUtils");
            Class[] sig1 = new Class[]{Class.forName("java.lang.String")};
            Constructor constructor = mrjOSTypeClass.getConstructor(sig1);
            Class[] sig2 = new Class[]{Class.forName("java.io.File"), mrjOSTypeClass, mrjOSTypeClass};
            Method fn = mrjFileUtilsClass.getMethod("setFileTypeAndCreator", sig2);
            Object[] args1 = new Object[]{(String.valueOf(type) + "    ").substring(0, 4)};
            Object osType = constructor.newInstance(args1);
            Object[] args2 = new Object[]{(String.valueOf(creator) + "    ").substring(0, 4)};
            Object creatorType = constructor.newInstance(args2);
            Object[] args3 = new Object[]{file, osType, creatorType};
            fn.invoke(null, args3);
        }
        catch (Exception mrjOSTypeClass) {
            // empty catch block
        }
    }

    public static boolean isSwingAvailable() {
        if (!swingChecked) {
            swingChecked = true;
            try {
                isSwingAvailable = Class.forName("javax.swing.JComponent") != null;
            }
            catch (Exception ex) {
                isSwingAvailable = false;
            }
        }
        return isSwingAvailable;
    }

    public static boolean isSunAudioAvailable() {
        if (!sunAudioChecked) {
            sunAudioChecked = true;
            try {
                isSunAudioAvailable = Class.forName("sun.audio.AudioPlayer") != null;
            }
            catch (Exception ex) {
                isSunAudioAvailable = false;
            }
        }
        return isSunAudioAvailable;
    }

    public static boolean isJMFAvailable() {
        if (!jmfChecked) {
            jmfChecked = true;
            try {
                isJMFAvailable = Class.forName("javax.media.Player") != null;
            }
            catch (Exception ex) {
                isJMFAvailable = false;
            }
        }
        return isJMFAvailable;
    }

    public static boolean areCollectionsAvailable() {
        if (!collectionsChecked) {
            collectionsChecked = true;
            try {
                areCollectionsAvailable = Class.forName("java.util.ArrayList") != null;
            }
            catch (Exception ex) {
                areCollectionsAvailable = false;
            }
        }
        return areCollectionsAvailable;
    }

    public static Frame getEnclosingFrame(Component comp) {
        while (comp != null && !(comp instanceof Frame)) {
            comp = comp.getParent();
        }
        return (Frame)comp;
    }
}

