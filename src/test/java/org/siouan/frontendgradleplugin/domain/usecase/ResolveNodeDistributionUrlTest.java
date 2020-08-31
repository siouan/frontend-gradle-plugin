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
import org.siouan.frontendgradleplugin.domain.model.Environment;
import org.siouan.frontendgradleplugin.domain.model.Platform;
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
            new DistributionDefinition(new Platform("i386", "Linux", new Environment(null, null)), VERSION, URL_ROOT,
                URL_PATH_PATTERN))).isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldFailWhenResolvingUrlWithSolarisOsAndJreArchEqualToSparc() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(new Platform("sparc", "Solaris", new Environment(null, null)), VERSION, URL_ROOT,
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
            .execute(new DistributionDefinition(new Platform("x86", "Windows NT", new Environment(null, null)), VERSION,
                URL_ROOT, URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-win-x86.zip");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithWindowsNTOsAndJreArchEqualToX64()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("x64", "Windows NT", new Environment(null, null)), VERSION,
                URL_ROOT, URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-win-x64.zip");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithLinuxOsAndJreArchEqualToAmd64()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("amd64", "Linux", new Environment(null, null)), VERSION,
                URL_ROOT, URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-linux-x64.tar.gz");
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithMacOsAndJreArchEqualToPPC()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("ppc", "Mac OS X", new Environment(null, null)), VERSION,
                URL_ROOT, URL_PATH_PATTERN))
            .toString()).isEqualTo("https://foo.bar/dist/v3.5.2-darwin-x64.tar.gz");
    }
}
