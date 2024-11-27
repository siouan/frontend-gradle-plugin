package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatformWithJvmArch;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatformWithOsName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlatformTest {

    @Test
    void should_return_false_when_checking_if_unrecognized_jvm_arch_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("OS_64_bits").is64BitsArch()).isFalse();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_x64_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_X64_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_x86_64_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_X86_64_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_amd64_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Amd64_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_aarch64_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Aarch64_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_ppc64_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_PPC64_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_ppc64le_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Ppc64Le_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_sparc_is_a_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Sparc_").is64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_arm_is_an_arm_32_bits_arch() {
        assertThat(aPlatformWithJvmArch("_armv_").isArm32BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_aarch64_is_an_arm_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Aarch64_").isArm64BitsArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_ppc64le_is_a_ppc_little_endian_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_Ppc64Le_").isPpc64BitsLeArch()).isTrue();
    }

    @Test
    void should_return_true_when_checking_if_jvm_arch_containing_s390x_is_a_s390x_64_bits_arch() {
        assertThat(aPlatformWithJvmArch("_S390x_").isS390X64BitsArch()).isTrue();
    }

    @Test
    void should_return_false_when_checking_if_os_name_containing_ibm_is_an_aix_os() {
        assertThat(aPlatformWithOsName("_Unix_").isAixOs()).isFalse();
    }

    @Test
    void should_return_true_when_checking_if_os_name_containing_aix_is_an_aix_os() {
        assertThat(aPlatformWithOsName("_AIX_").isAixOs()).isTrue();
    }

    @Test
    void should_return_false_when_checking_if_os_name_containing_unix_is_a_linux_os() {
        assertThat(aPlatformWithOsName("_Unix_").isLinuxOs()).isFalse();
    }

    @Test
    void should_return_true_when_checking_if_os_name_containing_linux_is_a_linux_os() {
        assertThat(aPlatformWithOsName("_Linux_").isLinuxOs()).isTrue();
    }

    @Test
    void should_return_false_when_checking_if_os_name_containing_macos_is_a_mac_os() {
        assertThat(aPlatformWithOsName("_MACOS_").isMacOs()).isFalse();
    }

    @Test
    void should_return_true_when_checking_if_os_name_containing_mac_os_is_a_mac_os() {
        assertThat(aPlatformWithOsName("_Mac OS_").isMacOs()).isTrue();
    }

    @Test
    void should_return_false_when_checking_if_os_name_containing_winnt_is_a_windows_os() {
        assertThat(aPlatformWithOsName("_WinNT_").isWindowsOs()).isFalse();
    }

    @Test
    void should_return_true_when_checking_if_os_name_containing_windows_is_a_windows_os() {
        assertThat(aPlatformWithOsName("_Windows_").isWindowsOs()).isTrue();
    }
}
