package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.siouan.frontendgradleplugin.domain.ChannelProvider;

/**
 * This class resolves the hash of a file, using the SHA-256 algorithm, and an internal buffer of 8 KB.
 */
public class HashFile {

    /**
     * Name of the algorithm used to hash the file.
     */
    private static final String ALGORITHM = "SHA-256";

    /**
     * Capacity of the buffer allocated to read the input file.
     */
    private static final int BUFFER_CAPACITY = 8192;

    private final ChannelProvider channelProvider;

    private final ConvertToHexadecimalString convertToHexadecimalString;

    private final MessageDigest messageDigest;

    /**
     * Builds a hasher using the SHA-256 algorithm.
     *
     * @param channelProvider Channel provider.
     * @param convertToHexadecimalString Converter of byte buffer to hexadecimal string.
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    public HashFile(final ChannelProvider channelProvider, final ConvertToHexadecimalString convertToHexadecimalString)
        throws NoSuchAlgorithmException {
        this.channelProvider = channelProvider;
        this.convertToHexadecimalString = convertToHexadecimalString;
        this.messageDigest = MessageDigest.getInstance(ALGORITHM);
    }

    /**
     * Computes the hash of the file at the given path.
     *
     * @param filePath The file to be hashed.
     * @return The hash as an hexadecimal string.
     * @throws IOException If the input file is not readable.
     */
    public String execute(final Path filePath) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        try (final SeekableByteChannel inputChannel = channelProvider.getSeekableByteChannel(filePath)) {
            int numberOfBytesRead = inputChannel.read(buffer);
            while (numberOfBytesRead != -1) {
                // Since JDK 9, ByteBuffer class overrides some methods and their return type in the Buffer class. To
                // ensure compatibility with JDK 8, calling the 'flipBuffer' and 'clearBuffer' methods forces using the
                // JDK 8 Buffer's methods signature, and avoids explicit casts.
                flipBuffer(buffer);
                messageDigest.update(buffer);
                clearBuffer(buffer);
                numberOfBytesRead = inputChannel.read(buffer);
            }
        }
        return convertToHexadecimalString.execute(messageDigest.digest());
    }

    ////////////////////
    // The 2 methods below force the use of the flip method and clear method in the Buffer class instead of the
    // ByteBuffer class. This is mandatory because the signature of each method is not the same in the ByteBuffer class
    // vs. the Buffer class, and it leads to NoSuchMethodError exceptions from JDK 9+ (see issue #55).
    ////////////////////
    private void flipBuffer(final Buffer buffer) {
        buffer.flip();
    }

    private void clearBuffer(final Buffer buffer) {
        buffer.clear();
    }
}
