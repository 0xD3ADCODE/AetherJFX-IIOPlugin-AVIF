package javafx.iio.plugin.avif.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import javafx.iio.plugin.avif.jna.AvifLibrary.avifDecoderData;

import java.util.Arrays;
import java.util.List;

/**
 * <i>native declaration : avif/avif.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class avifDecoder extends Structure {
    /**
     * @see AvifLibrary.avifCodecChoice
     * C type : avifCodecChoice
     */
    public int codecChoice;
    public int maxThreads;
    /**
     * @see AvifLibrary.avifDecoderSource
     * C type : avifDecoderSource
     */
    public int requestedSource;
    /**
     * C type : avifBool
     */
    public int allowProgressive;
    /**
     * C type : avifBool
     */
    public int allowIncremental;
    /**
     * C type : avifBool
     */
    public int ignoreExif;
    /**
     * C type : avifBool
     */
    public int ignoreXMP;
    public int imageSizeLimit;
    // This represents the maximum dimension of an image (width or height) that libavif should
    // attempt to decode. It defaults to AVIF_DEFAULT_IMAGE_DIMENSION_LIMIT. Set it to 0 to ignore
    // the limit.
    public int imageDimensionLimit;
    public int imageCountLimit;
    /**
     * C type : avifStrictFlags
     */
    public int strictFlags;
    /**
     * C type : avifImage*
     */
    public avifImage.ByReference image;
    /**
     * 0-based
     */
    public int imageIndex;
    /**
     * Always 1 for non-progressive, non-sequence AVIFs.
     */
    public int imageCount;
    /**
     * @see AvifLibrary.avifProgressiveState
     * See avifProgressiveState declaration<br>
     * C type : avifProgressiveState
     */
    public int progressiveState;
    /**
     * C type : avifImageTiming
     */
    public avifImageTiming imageTiming;
    /**
     * timescale of the media (Hz)
     */
    public long timescale;
    /**
     * duration of a single playback of the image sequence in seconds
     */
    public double duration;
    /**
     * duration of a single playback of the image sequence in "timescales"
     */
    public long durationInTimescales;
    /**
     * number of times the sequence has to be repeated. This can also be one of
     */
    public int repetitionCount;
    /**
     * C type : avifBool
     */
    public int alphaPresent;
    /**
     * C type : avifIOStats
     */
    public avifIOStats ioStats;
    /**
     * C type : avifDiagnostics
     */
    public avifDiagnostics diag;
    /**
     * C type : avifIO*
     */
    public avifIO.ByReference io;
    /**
     * C type : avifDecoderData*
     */
    public avifDecoderData data;
    /**
     * C type : avifBool
     */
    public int imageSequenceTrackPresent;

    public avifDecoder() {
        super();
    }

    protected List<String> getFieldOrder() {
        return Arrays.asList("codecChoice", "maxThreads", "requestedSource", "allowProgressive", "allowIncremental", "ignoreExif", "ignoreXMP", "imageSizeLimit", "imageDimensionLimit", "imageCountLimit", "strictFlags", "image", "imageIndex", "imageCount", "progressiveState", "imageTiming", "timescale", "duration", "durationInTimescales", "repetitionCount", "alphaPresent", "ioStats", "diag", "io", "data", "imageSequenceTrackPresent");
    }

    public avifDecoder(Pointer peer) {
        super(peer);
    }

    public static class ByReference extends avifDecoder implements Structure.ByReference {
    }

    public static class ByValue extends avifDecoder implements Structure.ByValue {
    }
}
