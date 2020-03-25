package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.siouan.frontendgradleplugin.domain.util.ByteUtils;

/**
 * This class resolves the hash of a file, using the SHA-256 algorithm, and an internal buffer of 8 KB.
 */
public class HashFile {

    /**
     * Capacity of the buffer allocated to read the input file.
     */
    private static final int BUFFER_CAPACITY = 8192;

    private final MessageDigest digest;

    /**
     * Builds a hasher using the SHA-256 algorithm.
     *
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    public HashFile() throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance("SHA-256");
    }

    /**
     * Computes the hash of the file if not already done.
     *
     * @param inputFile The file to be hashed.
     * @return The hash as an hexadecimal string.
     * @throws IOException If the input file is not readable.
     */
    public String execute(final Path inputFile) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        try (final SeekableByteChannel inputChannel = Files.newByteChannel(inputFile)) {
            int numberOfBytesRead = inputChannel.read(buffer);
            while (numberOfBytesRead != -1) {
                // Since JDK 9, ByteBuffer class overrides some methods and their return type in the Buffer class. To
                // ensure compatibility with JDK 8, calling the 'flipBuffer' and 'clearBuffer' methods forces using the
                // JDK 8 Buffer's methods signature, and avoids explicit casts.
                flipBuffer(buffer);
                digest.update(buffer);
                clearBuffer(buffer);
                numberOfBytesRead = inputChannel.read(buffer);
            }
        }
        return ByteUtils.toHexadecimalString(digest.digest());
    }

    private void flipBuffer(Buffer buffer) {
        buffer.flip();
    }

    private void clearBuffer(Buffer buffer) {
        buffer.clear();
    }
}
