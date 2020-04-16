package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConvertToHexadecimalStringTest {

    @InjectMocks
    private ConvertToHexadecimalString usecase;

    @Test
    void shouldConvertEmptyByteBufferToEmptyString() {
        assertThat(usecase.execute(new byte[] {})).isEqualTo("");
    }

    @Test
    void shouldConvertByteBufferToHexadecimalString() {
        assertThat(usecase.execute(new byte[] {0x7f, 0xa, 0x6e, -128})).isEqualTo("7f0a6e80");
    }
}
