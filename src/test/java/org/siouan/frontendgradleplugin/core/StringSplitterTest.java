package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link StringSplitter} class.
 *
 * @since 1.4.0
 */
class StringSplitterTest {

    @Test
    void shouldSplitString() {
        assertThat(new StringSplitter().execute(" str1 str2 ", ' ', '\\')).containsExactly("str1", "str2");
    }

    @Test
    void shouldSplitStringAndIgnoreEscapedSeparators() {
        assertThat(new StringSplitter().execute("\\ str1\\  str2\\ ", ' ', '\\')).containsExactly(" str1 ", "str2 ");
    }
}
