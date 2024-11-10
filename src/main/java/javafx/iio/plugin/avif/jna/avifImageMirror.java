package javafx.iio.plugin.avif.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

/**
 * <i>native declaration : avif/avif.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class avifImageMirror extends Structure {
    public byte axis;

    public avifImageMirror() {
        super();
    }

    protected List<String> getFieldOrder() {
        return Collections.singletonList("axis");
    }

    public avifImageMirror(byte axis) {
        super();
        this.axis = axis;
    }

    public avifImageMirror(Pointer peer) {
        super(peer);
    }

    public static class ByReference extends avifImageMirror implements Structure.ByReference {
    }

    public static class ByValue extends avifImageMirror implements Structure.ByValue {
    }
}