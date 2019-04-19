package org.siouan.frontendgradleplugin.util;

import org.mockito.ArgumentMatcher;

/**
 * A matcher of a {@link java.lang.String} argument ignoring case, and considering {@code null} values as equivalent.
 */
public class CaseInsensitiveStringMatcher implements ArgumentMatcher<String> {

    private final String expectedValue;

    public CaseInsensitiveStringMatcher(final String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean matches(final String argument) {
        return ((expectedValue == null) && (argument == null)) || ((expectedValue != null) && expectedValue
            .equalsIgnoreCase(argument));
    }
}
