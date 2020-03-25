package org.siouan.frontendgradleplugin.domain.util;

import javax.annotation.Nonnull;

/**
 * This class provides utilities for file management.
 *
 * @since 1.4.0
 */
public final class ByteUtils {

    private ByteUtils() {
    }

    /**
     * Converts a binary buffer into an hexadecimal string, with a lower case.
     *
     * @param buffer Buffer.
     * @return Hexadecimal string.
     */
    @Nonnull
    public static String toHexadecimalString(@Nonnull final byte[] buffer) {
        final StringBuilder hexadecimalString = new StringBuilder();
        for (final byte digit : buffer) {
            final String hexadecimalDigit = Integer.toHexString(0xff & digit);
            if (hexadecimalDigit.length() == 1) {
                hexadecimalString.append(0);
            }
            hexadecimalString.append(hexadecimalDigit);
        }
        return hexadecimalString.toString();
    }
}
