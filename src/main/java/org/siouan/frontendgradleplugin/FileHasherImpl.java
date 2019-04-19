package org.siouan.frontendgradleplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

    public String hash(final File inputFile) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        try (final FileInputStream inputStream = new FileInputStream(inputFile)) {
            final FileChannel inputChannel = inputStream.getChannel();
            int numberOfBytesRead = inputChannel.read(buffer);
            while (numberOfBytesRead != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.clear();
                numberOfBytesRead = inputChannel.read(buffer);
            }
        }
        return toHexadecimalString(digest.digest());
    }

    /**
     * Converts a binary hash into an hexadecimal string, with a lower case.
     *
     * @param hash Hash.
     * @return Hexadecimal string.
     */
    private String toHexadecimalString(final byte[] hash) {
        final StringBuffer hexadecimalString = new StringBuffer();
        for (byte digit : hash) {
            String hexadecimalDigit = Integer.toHexString(0xff & digit);
            if (hexadecimalDigit.length() == 1) {
                hexadecimalString.append(0);
            }
            hexadecimalString.append(hexadecimalDigit);
        }
        return hexadecimalString.toString();
    }
}
