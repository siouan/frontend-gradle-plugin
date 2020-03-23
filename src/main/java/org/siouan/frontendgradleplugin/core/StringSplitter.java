package org.siouan.frontendgradleplugin.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * A splitter of strings.
 *
 * @since 1.4.0
 */
public final class StringSplitter {

    /**
     * Splits the given argument using the separator character. The escape character is used to ignore unrelevant
     * separators. In this case, the separator character is kept, and the preceding escape character is removed from the
     * token.
     *
     * @param arg The argument to split.
     * @param separatorChar Separator character.
     * @param escapeChar Escape character to 'cancel' a separator character.
     * @return A list of tokens.
     * @since 1.4.0
     */
    @Nonnull
    public List<String> execute(@Nonnull final String arg, final char separatorChar, final char escapeChar) {
        final List<String> tokens = new ArrayList<>();
        char previousChar;
        char currentChar = 0;
        int tokenStartIndex = 0;
        int i = 0;
        String token = "";
        boolean lastToken;
        boolean nonEscapedSeparatorFound;
        boolean tokenFound;
        while (i < arg.length()) {
            if (i == 0) {
                previousChar = arg.charAt(i);
                currentChar = previousChar;
            } else {
                previousChar = currentChar;
                currentChar = arg.charAt(i);
            }

            lastToken = (i == (arg.length() - 1));
            nonEscapedSeparatorFound = (currentChar == separatorChar) && (previousChar != escapeChar);
            tokenFound = lastToken || nonEscapedSeparatorFound;

            if (tokenFound) {
                if (lastToken) {
                    token = arg.substring(tokenStartIndex, nonEscapedSeparatorFound ? i : i + 1);
                } else {
                    token = arg.substring(tokenStartIndex, i);
                    tokenStartIndex = i + 1;
                }

                if (!token.isEmpty()) {
                    tokens.add(
                        token.replace(String.valueOf(escapeChar) + separatorChar, String.valueOf(separatorChar)));
                }
            }
            i++;
        }
        return tokens;
    }
}
