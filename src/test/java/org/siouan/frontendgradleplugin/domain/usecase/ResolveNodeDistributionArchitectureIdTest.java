package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionArchitectureIdTest {

    @InjectMocks
    private ResolveNodeDistributionArchitectureId usecase;

    @Test
    void shouldNotBeResolvedWhenOsIsLinuxOsAndJreArchIsI386() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("i386", "Linux"))).isEmpty();
    }

    @Test
    void shouldNotBeResolvedWhenOsIsSolarisOsAndJreArchIsSparc() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("sparc", "Solaris"))).isEmpty();
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithWindowsNTOsAndJreArchEqualToX86() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("x86", "Windows NT"))).contains("win-x86");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithWindowsNTOsAndJreArchEqualToX64() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("x64", "Windows NT"))).contains("win-x64");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithLinuxOsAndJreArchEqualToAmd64() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("amd64", "Linux"))).contains("linux-x64");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithMacOsAndJreArchEqualToPPC() {
        assertThat(usecase.execute(PlatformFixture.aPlatform("ppc", "Mac OS X"))).contains("darwin-x64");
    }
}
