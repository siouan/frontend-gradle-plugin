package org.siouan.frontendgradleplugin.domain.usecase;

import javax.annotation.Nonnull;

/**
 * Converts a binary buffer into an hexadecimal string.
 *
 * @since 1.4.0
 */
public class ConvertToHexadecimalString {

    /**
     * Converts a binary buffer into an hexadecimal string, with a lower case.
     *
     * @param buffer Buffer.
     * @return Hexadecimal string.
     */
    @Nonnull
    public String execute(@Nonnull final byte[] buffer) {
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
