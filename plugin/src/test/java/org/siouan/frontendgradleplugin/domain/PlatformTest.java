package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemJvmArch;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemOsName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlatformTest {

    @Test
    void should_return_false_when_jvm_arch_is_not_64_bits() {
        assertThat(aPlatform("OS_64_bits", getSystemOsName()).is64BitsArch()).isFalse();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_x64() {
        assertThat(aPlatform("_X64_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_x86_64() {
        assertThat(aPlatform("_X86_64_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_amd64() {
        assertThat(aPlatform("_Amd64_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_aarch64() {
        assertThat(aPlatform("_Aarch64_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_ppc() {
        assertThat(aPlatform("_PPC_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_jvm_arch_contains_sparc() {
        assertThat(aPlatform("_Sparc_", getSystemOsName()).is64BitsArch()).isTrue();
    }

    @Test
    void should_confirm_arm_64__arch_when_jvm_arch_contains_aarch64() {
        assertThat(aPlatform("_Aarch64_", getSystemOsName()).isArm64BitsArch()).isTrue();
    }

    @Test
    void should_return_false_when_os_name_does_not_contain_linux() {
        assertThat(aPlatform(getSystemJvmArch(), "_Unix_").isLinuxOs()).isFalse();
    }

    @Test
    void should_return_true_when_os_name_contains_linux() {
        assertThat(aPlatform(getSystemJvmArch(), "_Linux_").isLinuxOs()).isTrue();
    }

    @Test
    void should_return_false_when_os_name_does_not_contain_mac_os() {
        assertThat(aPlatform(getSystemJvmArch(), "_MACOS_").isMacOs()).isFalse();
    }

    @Test
    void should_return_true_when_os_name_contains_mac_os() {
        assertThat(aPlatform(getSystemJvmArch(), "_Mac OS_").isMacOs()).isTrue();
    }

    @Test
    void should_return_false_when_os_name_does_not_contain_windows() {
        assertThat(aPlatform(getSystemJvmArch(), "_WinNT_").isWindowsOs()).isFalse();
    }

    @Test
    void should_return_true_when_os_name_contains_windows() {
        assertThat(aPlatform(getSystemJvmArch(), "_Windows_").isWindowsOs()).isTrue();
    }
}
