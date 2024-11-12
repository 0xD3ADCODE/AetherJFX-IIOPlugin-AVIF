package javafx.iio.plugin.avif;

import javafx.iio.*;

import javax.imageio.IIOException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class AVIFLoader extends IIOLoader {
    private static final String FORMAT_NAME = "AVIF";
    private static final List<String> EXTENSIONS = List.of("avif");
    private static final List<IIOSignature> SIGNATURES = List.of(new IIOSignature(4, (byte) 'f', (byte) 't', (byte) 'y', (byte) 'p', (byte) 'a', (byte) 'v', (byte) 'i', (byte) 'f'));

    public static void register() {
        IIO.registerImageLoader(FORMAT_NAME, EXTENSIONS, SIGNATURES, EXTENSIONS, AVIFLoader::new);
    }

    private AVIFLoader(InputStream stream) {
        super(stream);
    }

    @Override
    public IIOImageFrame decode(int imageIndex, int rWidth, int rHeight, boolean preserveAspectRatio, boolean smooth) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[8192];
            while (true) {
                int read = super.stream.read(bytes, 0, bytes.length);
                if (read < 0) break;
                baos.write(bytes, 0, read);
            }

            int length = baos.size();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
            byteBuffer.put(baos.toByteArray(), 0, length);

            IIOImageFrame imageFrame = Avif.getInstance().decode(byteBuffer, length);

            int[] outWH = IIOImageTools.computeDimensions(imageFrame.getWidth(), imageFrame.getHeight(), rWidth, rHeight, preserveAspectRatio);
            rWidth = outWH[0];
            rHeight = outWH[1];

            return imageFrame.getWidth() != rWidth || imageFrame.getHeight() != rHeight
                    ? IIOImageTools.scaleImageFrame(imageFrame, rWidth, rHeight, smooth)
                    : imageFrame;
        } catch (Exception e) {
            throw new IIOException(e.getMessage(), e);
        }
    }
}