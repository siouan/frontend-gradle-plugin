package org.siouan.frontendgradleplugin.domain.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A splitter of strings.
 *
 * @since 1.4.0
 */
public final class StringSplitter {

    private final char separatorChar;

    private final char escapeChar;

    /**
     * Builds a string splitter. The escape character is used to ignore unrelevant separators. In this case, the
     * separator character is kept, and the preceding escape character is removed from the token.
     *
     * @param separatorChar Separator character.
     * @param escapeChar Escape character to 'cancel' a separator character.
     */
    public StringSplitter(final char separatorChar, final char escapeChar) {
        this.separatorChar = separatorChar;
        this.escapeChar = escapeChar;
    }

    /**
     * Splits the given argument.
     *
     * @param arg The argument to split.
     * @return List of tokens.
     */
    @Nonnull
    public List<String> execute(@Nonnull final String arg) {
        final List<String> tokens = new ArrayList<>();
        int i = 0;
        char previousChar;
        char currentChar = 0;
        int tokenStartIndex = 0;
        String token;
        while (i < arg.length()) {
            if (i == 0) {
                previousChar = arg.charAt(i);
                currentChar = previousChar;
            } else {
                previousChar = currentChar;
                currentChar = arg.charAt(i);
            }

            token = parseToken(arg, i, isValidSeparatorChar(currentChar, previousChar), tokenStartIndex);
            if (token != null) {
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
                tokenStartIndex = i + 1;
            }
            i++;
        }
        return tokens;
    }

    /**
     * Whether the current character is a valid separator, i.e. non-escaped with the previous character.
     *
     * @param currentChar Current character.
     * @param previousChar Previous character.
     * @return {code true} if the current character is a valid separator.
     */
    private boolean isValidSeparatorChar(final char currentChar, final char previousChar) {
        return (currentChar == separatorChar) && (previousChar != escapeChar);
    }

    /**
     * Parses a token if found.
     *
     * @param arg String to parse.
     * @param currentCharIndex Index of the current character.
     * @param validSeparator Whether the current character is a valid separator.
     * @param tokenStartIndex Index of the first character of the token being processed.
     * @return Token, {@code null} if not resolved yet (e.g. end of string not reached, or next separator not found).
     */
    @Nullable
    private String parseToken(@Nonnull final String arg, final int currentCharIndex, final boolean validSeparator,
        final int tokenStartIndex) {
        final boolean lastToken = (currentCharIndex == (arg.length() - 1));
        final boolean tokenFound = lastToken || validSeparator;

        if (!tokenFound) {
            return null;
        }

        final String token;
        if (lastToken) {
            token = arg.substring(tokenStartIndex, validSeparator ? currentCharIndex : currentCharIndex + 1);
        } else {
            token = arg.substring(tokenStartIndex, currentCharIndex);
        }

        return token.replace(String.valueOf(escapeChar) + separatorChar, String.valueOf(separatorChar));
    }
}
