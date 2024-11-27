package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link StringSplitter} class.
 *
 * @since 1.4.0
 */
class StringSplitterTest {

    private StringSplitter stringSplitter;

    @BeforeEach
    void setUp() {
        stringSplitter = new StringSplitter(' ', '\\');
    }

    @Test
    void should_split_string() {
        assertThat(stringSplitter.execute(" str1 str2 ")).containsExactly("str1", "str2");
    }

    @Test
    void should_split_string_and_ignore_escaped_separators() {
        assertThat(stringSplitter.execute("\\ str1\\  str2\\ ")).containsExactly(" str1 ", "str2 ");
    }
}
