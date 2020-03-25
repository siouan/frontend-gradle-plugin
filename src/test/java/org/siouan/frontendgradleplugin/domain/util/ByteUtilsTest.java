package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ByteUtilsTest {

    @Test
    void shouldConvertEmptyByteBufferToEmptyString() {
        assertThat(ByteUtils.toHexadecimalString(new byte[] {})).isEqualTo("");
    }

    @Test
    void shouldConvertByteBufferToHexadecimalString() {
        assertThat(ByteUtils.toHexadecimalString(new byte[] {0x7f, 0xa, 0x6e, -128})).isEqualTo("7f0a6e80");
    }
}
