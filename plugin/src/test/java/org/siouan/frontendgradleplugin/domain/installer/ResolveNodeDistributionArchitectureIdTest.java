package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionArchitectureIdTest {

    @InjectMocks
    private ResolveNodeDistributionArchitectureId usecase;

    @Test
    void should_not_be_resolved_when_os_is_linux_os_and_jre_arch_is_i386() {
        assertThat(usecase.execute(aPlatform("i386", "Linux"))).isEmpty();
    }

    @Test
    void should_not_be_resolved_when_os_is_mac_os_and_jre_arch_is_i386() {
        assertThat(usecase.execute(aPlatform("i386", "Mac OS"))).isEmpty();
    }

    @Test
    void should_not_be_resolved_when_os_is_solaris_os_and_jre_arch_is_sparc() {
        assertThat(usecase.execute(aPlatform("sparc", "Solaris"))).isEmpty();
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_windows_nt_os_and_jre_arch_equal_to_x86() {
        assertThat(usecase.execute(aPlatform("x86", "Windows NT"))).contains("win-x86");
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_windows_nt_os_and_jre_arch_equal_to_x64() {
        assertThat(usecase.execute(aPlatform("x64", "Windows NT"))).contains("win-x64");
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_linux_os_and_jre_arch_equal_to_amd64() {
        assertThat(usecase.execute(aPlatform("amd64", "Linux"))).contains("linux-x64");
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_linux_and_jre_arch_equal_to_arm() {
        assertThat(usecase.execute(aPlatform("armv", "Linux"))).contains("linux-armv7l");
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_mac_os_and_jre_arch_equal_to_ppc() {
        assertThat(usecase.execute(aPlatform("ppc", "Mac OS X"))).contains("darwin-x64");
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_mac_os_and_jre_arch_equal_to_arm64() {
        assertThat(usecase.execute(aPlatform("aarch64", "Mac OS X"))).contains("darwin-arm64");
    }
}
