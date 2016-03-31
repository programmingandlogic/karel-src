/*
 * Decompiled with CFR 0_114.
 */
package acm.util;

import java.applet.AudioClip;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class SunAudioClip
implements AudioClip {
    private static boolean initialized;
    private static Class audioPlayerClass;
    private static Class audioStreamClass;
    private static Class audioDataClass;
    private static Class audioDataStreamClass;
    private static Class continuousAudioDataStreamClass;
    private static Constructor audioDataConstructor;
    private static Constructor audioDataStreamConstructor;
    private static Constructor continuousAudioDataStreamConstructor;
    private static Method getData;
    private Object player;
    private Object audioData;
    private Object audioDataStream;
    private Object continuousAudioDataStream;
    private Method audioPlayerStart;
    private Method audioPlayerStop;

    public SunAudioClip(InputStream in) {
        if (!initialized) {
            SunAudioClip.initStaticData();
            initialized = true;
        }
        try {
            Object[] args = new Object[]{in};
            Object audioStream = audioDataConstructor.newInstance(args);
            this.audioData = getData.invoke(audioStream, new Object[0]);
            this.player = audioPlayerClass.getField("player").get(null);
            Class[] inputStreamTypes = new Class[]{Class.forName("java.io.InputStream")};
            this.audioPlayerStart = this.player.getClass().getMethod("start", inputStreamTypes);
            this.audioPlayerStop = this.player.getClass().getMethod("stop", inputStreamTypes);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error: " + ex);
        }
    }

    public void play() {
        try {
            Object[] args = new Object[]{this.audioData};
            args[0] = this.audioDataStream = audioDataStreamConstructor.newInstance(args);
            this.audioPlayerStart.invoke(this.player, args);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error: " + ex);
        }
    }

    public void loop() {
        try {
            Object[] args = new Object[]{this.audioData};
            args[0] = this.continuousAudioDataStream = continuousAudioDataStreamConstructor.newInstance(args);
            this.audioPlayerStart.invoke(this.player, args);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error: " + ex);
        }
    }

    public void stop() {
        try {
            Object[] args = new Object[1];
            if (this.continuousAudioDataStream != null) {
                args[0] = this.audioDataStream;
                this.audioPlayerStop.invoke(this.player, args);
            }
            if (this.audioDataStream != null) {
                args[0] = this.continuousAudioDataStream;
                this.audioPlayerStop.invoke(this.player, args);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Error: " + ex);
        }
    }

    private static void initStaticData() {
        try {
            audioPlayerClass = Class.forName("sun.audio.AudioPlayer");
            audioStreamClass = Class.forName("sun.audio.AudioStream");
            audioDataClass = Class.forName("sun.audio.AudioData");
            audioDataStreamClass = Class.forName("sun.audio.AudioDataStream");
            continuousAudioDataStreamClass = Class.forName("sun.audio.ContinuousAudioDataStream");
            Class[] inputStreamTypes = new Class[]{Class.forName("java.io.InputStream")};
            audioDataConstructor = audioStreamClass.getConstructor(inputStreamTypes);
            getData = audioStreamClass.getMethod("getData", new Class[0]);
            Class[] audioDataTypes = new Class[]{audioDataClass};
            audioDataStreamConstructor = audioDataStreamClass.getConstructor(audioDataTypes);
            continuousAudioDataStreamConstructor = continuousAudioDataStreamClass.getConstructor(audioDataTypes);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error: " + ex);
        }
    }
}

