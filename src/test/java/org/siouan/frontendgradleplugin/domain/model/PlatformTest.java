package org.siouan.frontendgradleplugin.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

@ExtendWith(MockitoExtension.class)
class PlatformTest {

    @Test
    void shouldReturnFalseWhenJvmArchIsNot64Bits() {
        assertThat(new Platform("OS_64_bits", SystemUtils.getSystemOsName(),
            new Environment(null, null)).is64BitsArch()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x64() {
        assertThat(
            new Platform("_X64_", SystemUtils.getSystemOsName(), new Environment(null, null)).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x86_64() {
        assertThat(new Platform("_X86_64_", SystemUtils.getSystemOsName(),
            new Environment(null, null)).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_amd64() {
        assertThat(new Platform("_Amd64_", SystemUtils.getSystemOsName(),
            new Environment(null, null)).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_ppc() {
        assertThat(
            new Platform("_PPC_", SystemUtils.getSystemOsName(), new Environment(null, null)).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_sparc() {
        assertThat(new Platform("_Sparc_", SystemUtils.getSystemOsName(),
            new Environment(null, null)).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_linux() {
        assertThat(
            new Platform(SystemUtils.getSystemJvmArch(), "_Unix_", new Environment(null, null)).isLinuxOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_linux() {
        assertThat(
            new Platform(SystemUtils.getSystemJvmArch(), "_Linux_", new Environment(null, null)).isLinuxOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_mac_os() {
        assertThat(
            new Platform(SystemUtils.getSystemJvmArch(), "_MACOS_", new Environment(null, null)).isMacOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_mac_os() {
        assertThat(
            new Platform(SystemUtils.getSystemJvmArch(), "_Mac OS_", new Environment(null, null)).isMacOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_windows() {
        assertThat(new Platform(SystemUtils.getSystemJvmArch(), "_WinNT_",
            new Environment(null, null)).isWindowsOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_windows() {
        assertThat(new Platform(SystemUtils.getSystemJvmArch(), "_Windows_", new Environment(null, null)).isWindowsOs())
            .isTrue();
    }
}
