package org.siouan.frontendgradleplugin.domain.installer;

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
    void should_convert_empty_byte_buffer_to_empty_string() {
        assertThat(usecase.execute(new byte[] {})).isEmpty();
    }

    @Test
    void should_convert_byte_buffer_to_hexadecimal_string() {
        assertThat(usecase.execute(new byte[] {0x7f, 0xa, 0x6e, -128})).isEqualTo("7f0a6e80");
    }
}
