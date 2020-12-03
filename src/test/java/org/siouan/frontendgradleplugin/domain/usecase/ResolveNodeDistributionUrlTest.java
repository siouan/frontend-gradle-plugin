package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionUrlTest {

    private static final String URL_ROOT = "https://foo.bar/dist/";

    private static final String URL_PATH_PATTERN = "vVERSION-ARCH.TYPE";

    private static final String VERSION = "3.5.2";

    @InjectMocks
    private ResolveNodeDistributionUrl usecase;

    @Test
    void shouldFailWhenResolvingUrlWithLinuxOsAndJreArchEqualToI386() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(PlatformFixture.aDefaultPlatform("i386", "Linux"), VERSION, URL_ROOT,
                URL_PATH_PATTERN))).isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldFailWhenResolvingUrlWithSolarisOsAndJreArchEqualToSparc() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(PlatformFixture.aDefaultPlatform("sparc", "Solaris"), VERSION, URL_ROOT,
                URL_PATH_PATTERN))).isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithPathPatternContainingNoToken()
        throws UnsupportedPlatformException, MalformedURLException {

        assertThat(usecase
            .execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, URL_ROOT, "node.zip"))
            .toString()).isEqualTo("https://foo.bar/dist/node.zip");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithWindowsNTOsAndJreArchEqualToX86()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(
                new DistributionDefinition(PlatformFixture.aDefaultPlatform("x86", "Windows NT"), VERSION, URL_ROOT,
                    URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-win-x86.zip");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithWindowsNTOsAndJreArchEqualToX64()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(
                new DistributionDefinition(PlatformFixture.aDefaultPlatform("x64", "Windows NT"), VERSION, URL_ROOT,
                    URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-win-x64.zip");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithLinuxOsAndJreArchEqualToAmd64()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(PlatformFixture.aDefaultPlatform("amd64", "Linux"), VERSION, URL_ROOT,
                URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-linux-x64.tar.gz");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithMacOsAndJreArchEqualToPPC()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(PlatformFixture.aDefaultPlatform("ppc", "Mac OS X"), VERSION, URL_ROOT,
                URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-darwin-x64.tar.gz");
    }
}
