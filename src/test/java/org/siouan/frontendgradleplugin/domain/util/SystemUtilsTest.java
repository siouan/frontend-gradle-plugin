package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SystemUtilsTest {

    @Test
    void shouldReturnANonNullStringWhenGettingJvmArch() {
        assertThat(SystemUtils.getSystemJvmArch()).isNotNull();
    }

    @Test
    void shouldReturnANonNullStringWhenGettingOsName() {
        assertThat(SystemUtils.getSystemOsName()).isNotNull();
    }
}
