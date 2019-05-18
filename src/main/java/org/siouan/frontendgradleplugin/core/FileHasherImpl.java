package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class resolves the hash of a file, using the SHA-256 algorithm, and an internal buffer of 8 KB.
 */
public class FileHasherImpl implements FileHasher {

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
    public FileHasherImpl() throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public String hash(final Path inputFile) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        try (final SeekableByteChannel inputChannel = Files.newByteChannel(inputFile)) {
            int numberOfBytesRead = inputChannel.read(buffer);
            while (numberOfBytesRead != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.clear();
                numberOfBytesRead = inputChannel.read(buffer);
            }
        }
        return Utils.toHexadecimalString(digest.digest());
    }
}
