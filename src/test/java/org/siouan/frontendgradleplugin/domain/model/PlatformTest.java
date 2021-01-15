package org.siouan.frontendgradleplugin.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class PlatformTest {

    @Test
    void shouldReturnFalseWhenJvmArchIsNot64Bits() {
        assertThat(
            PlatformFixture.aDefaultPlatform("OS_64_bits", SystemUtils.getSystemOsName()).is64BitsArch()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x64() {
        assertThat(PlatformFixture.aDefaultPlatform("_X64_", SystemUtils.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x86_64() {
        assertThat(PlatformFixture.aDefaultPlatform("_X86_64_", SystemUtils.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_amd64() {
        assertThat(PlatformFixture.aDefaultPlatform("_Amd64_", SystemUtils.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_ppc() {
        assertThat(PlatformFixture.aDefaultPlatform("_PPC_", SystemUtils.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_sparc() {
        assertThat(PlatformFixture.aDefaultPlatform("_Sparc_", SystemUtils.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_linux() {
        assertThat(PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_Unix_").isLinuxOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_linux() {
        assertThat(PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_Linux_").isLinuxOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_mac_os() {
        assertThat(PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_MACOS_").isMacOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_mac_os() {
        assertThat(PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_Mac OS_").isMacOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_windows() {
        assertThat(PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_WinNT_").isWindowsOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_windows() {
        assertThat(
            PlatformFixture.aDefaultPlatform(SystemUtils.getSystemJvmArch(), "_Windows_").isWindowsOs()).isTrue();
    }
}
