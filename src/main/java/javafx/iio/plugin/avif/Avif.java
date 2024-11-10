// Copyright 2022 Google LLC. All rights reserved.
// SPDX-License-Identifier: BSD-2-Clause

package javafx.iio.plugin.avif;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import javafx.iio.IIOImageFrame;
import javafx.iio.IIOImageMetadata;
import javafx.iio.IIOImageType;
import javafx.iio.plugin.avif.jna.AvifLibrary;
import javafx.iio.plugin.avif.jna.avifDecoder;
import javafx.iio.plugin.avif.jna.avifRGBImage;
import javafx.iio.plugin.avif.jna.avifROData;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.ByteBuffer;

import static java.lang.System.getLogger;

/**
 * An AVIF Decoder.
 *
 * @see "AVIF Specification: https://aomediacodec.github.io/av1-avif/."
 */
class Avif {

    private static final Logger logger = getLogger(Avif.class.getName());

    private static final Avif avif = new Avif();

    // This is a utility class and cannot be instantiated.
    private Avif() {
        String version = AvifLibrary.INSTANCE.avifVersion();
        logger.log(Level.DEBUG, "libavif version: " + version);
    }

    public static Avif getInstance() {
        return avif;
    }

    /**
     * Returns true if the bytes in the buffer seem like an AVIF image.
     *
     * @param encoded The encoded image. buffer.position() must be 0.
     * @return true if the bytes seem like an AVIF image, false otherwise.
     */
    public static boolean isAvifImage(ByteBuffer encoded, int length) {
        avifROData data = new avifROData();
        data.data = Native.getDirectBufferPointer(encoded);
        data.size.setValue(length);
        return AvifLibrary.INSTANCE.avifPeekCompatibleFileType(data) == AvifLibrary.AVIF_TRUE;
    }

    /**
     * Decodes the AVIF image into the bitmap.
     *
     * @param encoded The encoded AVIF image. encoded.position() must be 0.
     * @param length  Length of the encoded buffer.
     * @return the decoded image.
     */
    public IIOImageFrame decode(ByteBuffer encoded, int length) {
        avifDecoder decoder = avif.createDecoderAndParse(Native.getDirectBufferPointer(encoded), length, Runtime.getRuntime().availableProcessors());
        int res = AvifLibrary.INSTANCE.avifDecoderNextImage(decoder);
        if (res != AvifLibrary.avifResult.AVIF_RESULT_OK) {
            throw new IllegalStateException(String.format("Failed to decode AVIF image. Status: %d", res));
        }

        avifRGBImage rgbImage = new avifRGBImage();
        AvifLibrary.INSTANCE.avifRGBImageSetDefaults(rgbImage, decoder.image);

        int bytes = 4;
        ByteBuffer nativeBuffer = ByteBuffer.allocateDirect(decoder.image.width * decoder.image.height * bytes);
        rgbImage.pixels = Native.getDirectBufferPointer(nativeBuffer);
        rgbImage.rowBytes = decoder.image.width * bytes;
        rgbImage.depth = 8;
        res = AvifLibrary.INSTANCE.avifImageYUVToRGB(decoder.image, rgbImage);
        if (res != AvifLibrary.avifResult.AVIF_RESULT_OK) {
            throw new IllegalStateException(String.format("Failed to convert YUV Pixels to RGB. Status: %d", res));
        }

        // because nativeBuffer doesn't have array()
        ByteBuffer localBuffer = ByteBuffer.allocate(nativeBuffer.capacity());
        localBuffer.put(nativeBuffer);

        IIOImageFrame imageFrame = new IIOImageFrame(
                IIOImageType.RGBA,
                ByteBuffer.wrap(localBuffer.array()),
                decoder.image.width,
                decoder.image.height,
                decoder.image.width * 4,
                null,
                new IIOImageMetadata(
                        null, Boolean.TRUE, null, null, null, null, null,
                        decoder.image.width, decoder.image.height,
                        null, null, null
                )
        );

        AvifLibrary.INSTANCE.avifDecoderDestroy(decoder);
        return imageFrame;
    }

    private avifDecoder createDecoderAndParse(Pointer buffer, int length, int threads) {
        avifDecoder decoder = AvifLibrary.INSTANCE.avifDecoderCreate();
        if (decoder == null) {
            throw new IllegalStateException("Failed to create AVIF Decoder.");
        }
        decoder.maxThreads = threads;
        decoder.ignoreXMP = AvifLibrary.AVIF_TRUE;
        decoder.ignoreExif = AvifLibrary.AVIF_TRUE;

        // Turn off 'clap' (clean aperture) property validation. The JNI wrapper
        // ignores the 'clap' property.
        decoder.strictFlags &= ~AvifLibrary.avifStrictFlag.AVIF_STRICT_CLAP_VALID;
        // Allow 'pixi' (pixel information) property to be missing. Older versions of
        // libheif did not add the 'pixi' item property to AV1 image items (See
        // crbug.com/1198455).
        decoder.strictFlags &= ~AvifLibrary.avifStrictFlag.AVIF_STRICT_PIXI_REQUIRED;

        int res = AvifLibrary.INSTANCE.avifDecoderSetIOMemory(decoder, buffer, new NativeLong(length));
        if (res != AvifLibrary.avifResult.AVIF_RESULT_OK) {
            throw new IllegalStateException("Failed to set AVIF IO to a memory reader.");
        }
        res = AvifLibrary.INSTANCE.avifDecoderParse(decoder);
        if (res != AvifLibrary.avifResult.AVIF_RESULT_OK) {
            throw new IllegalStateException(String.format("Failed to parse AVIF image: %s.", AvifLibrary.INSTANCE.avifResultToString(res)));
        }
        return decoder;
    }
}
