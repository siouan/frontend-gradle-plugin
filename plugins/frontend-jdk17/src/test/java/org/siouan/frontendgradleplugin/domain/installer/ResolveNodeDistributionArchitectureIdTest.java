package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.AIX_PPC64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.LINUX_ARM32_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.LINUX_ARM64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.LINUX_PPC64LE_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.LINUX_S390X_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.LINUX_X64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.MACOS_ARM64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.MACOS_X64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.WINDOWS_ARM64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.WINDOWS_X64_ARCH;
import static org.siouan.frontendgradleplugin.domain.installer.ResolveNodeDistributionArchitectureId.WINDOWS_X86_ARCH;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionArchitectureIdTest {

    @InjectMocks
    private ResolveNodeDistributionArchitectureId usecase;

    @Test
    void should_not_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_i386() {
        assertThat(usecase.execute(aPlatform("i386", "Linux"))).isEmpty();
    }

    @Test
    void should_not_resolve_architecture_id_when_os_is_mac_and_jvm_arch_is_i386() {
        assertThat(usecase.execute(aPlatform("i386", "Mac OS"))).isEmpty();
    }

    @Test
    void should_not_resolve_architecture_id_when_os_is_solaris_and_jvm_arch_is_sparc() {
        assertThat(usecase.execute(aPlatform("sparc", "Solaris"))).isEmpty();
    }

    @Test
    void should_resolve_architecture_id_when_os_is_aix_and_jvm_arch_is_ppc64() {
        assertThat(usecase.execute(aPlatform("ppc64", "AIX"))).contains(AIX_PPC64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_windows_nt_and_jvm_arch_is_x86() {
        assertThat(usecase.execute(aPlatform("x86", "Windows NT"))).contains(WINDOWS_X86_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_windows_nt_and_jvm_arch_is_x64() {
        assertThat(usecase.execute(aPlatform("x64", "Windows NT"))).contains(WINDOWS_X64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_windows_nt_and_jvm_arch_is_arm64() {
        assertThat(usecase.execute(aPlatform("aarch64", "Windows NT"))).contains(WINDOWS_ARM64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_amd64() {
        assertThat(usecase.execute(aPlatform("amd64", "Linux"))).contains(LINUX_X64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_aarch64() {
        assertThat(usecase.execute(aPlatform("aarch64", "Linux"))).contains(LINUX_ARM64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_armv() {
        assertThat(usecase.execute(aPlatform("armv", "Linux"))).contains(LINUX_ARM32_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_ppc64le() {
        assertThat(usecase.execute(aPlatform("ppc64le", "Linux"))).contains(LINUX_PPC64LE_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_linux_and_jvm_arch_is_s390x() {
        assertThat(usecase.execute(aPlatform("s390x", "Linux"))).contains(LINUX_S390X_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_mac_and_jvm_arch_is_amd64() {
        assertThat(usecase.execute(aPlatform("amd64", "Mac OS X"))).contains(MACOS_X64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_mac_and_jvm_arch_is_x64() {
        assertThat(usecase.execute(aPlatform("x64", "Mac OS X"))).contains(MACOS_X64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_mac_and_jvm_arch_is_x86_64() {
        assertThat(usecase.execute(aPlatform("x86_64", "Mac OS X"))).contains(MACOS_X64_ARCH);
    }

    @Test
    void should_resolve_architecture_id_when_os_is_mac_and_jvm_arch_is_aarch64() {
        assertThat(usecase.execute(aPlatform("aarch64", "Mac OS X"))).contains(MACOS_ARM64_ARCH);
    }
}
