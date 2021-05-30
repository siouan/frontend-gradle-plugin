package org.siouan.frontendgradleplugin.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class PlatformTest {

    @Test
    void shouldReturnFalseWhenJvmArchIsNot64Bits() {
        assertThat(PlatformFixture.aPlatform("OS_64_bits", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x64() {
        assertThat(PlatformFixture.aPlatform("_X64_", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_x86_64() {
        assertThat(PlatformFixture.aPlatform("_X86_64_", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_amd64() {
        assertThat(PlatformFixture.aPlatform("_Amd64_", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_aarch64() {
        assertThat(PlatformFixture.aPlatform("_Aarch64_", SystemPropertyFixture.getSystemOsName()).is64BitsArch())
            .isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_ppc() {
        assertThat(PlatformFixture.aPlatform("_PPC_", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenJvmArchContains_sparc() {
        assertThat(PlatformFixture.aPlatform("_Sparc_", SystemPropertyFixture.getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void shouldConfirmArm64ArchWhenJvmArchContains_aarch64() {
        assertThat(PlatformFixture.aPlatform("_Aarch64_", SystemPropertyFixture.getSystemOsName()).isArm64BitsArch()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_linux() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_Unix_").isLinuxOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_linux() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_Linux_").isLinuxOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_mac_os() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_MACOS_").isMacOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_mac_os() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_Mac OS_").isMacOs()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOsNameDoesNotContain_windows() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_WinNT_").isWindowsOs()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenOsNameContains_windows() {
        assertThat(PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "_Windows_").isWindowsOs()).isTrue();
    }
}
