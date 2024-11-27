package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
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
     * Builds a hash service using the SHA-256 algorithm.
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
                buffer.flip();
                messageDigest.update(buffer);
                buffer.clear();
                numberOfBytesRead = inputChannel.read(buffer);
            }
        }
        return convertToHexadecimalString.execute(messageDigest.digest());
    }
}
