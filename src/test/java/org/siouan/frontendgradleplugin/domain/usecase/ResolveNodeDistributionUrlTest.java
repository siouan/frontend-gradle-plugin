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

    private static final String URL_PATTERN = "https://foo.bar/vVERSION-ARCH.TYPE";

    private static final String VERSION = "3.5.2";

    @InjectMocks
    private ResolveNodeDistributionUrl usecase;

    @Test
    void shouldFailWhenOsIsLinuxAndJreArchIsI386() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(new Platform("i386", "Linux", new Environment(null, null)), VERSION,
                URL_PATTERN))).isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldFailWhenOsIsSolarisAndJreArchIsSparc() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(new Platform("sparc", "Solaris", new Environment(null, null)), VERSION,
                URL_PATTERN))).isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldReturnIdenticalDistributionUrlWhenResolvingFromPatternWithoutToken()
        throws UnsupportedPlatformException, MalformedURLException {
        final String distributionUrl = "http://url";

        assertThat(usecase
            .execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, distributionUrl))
            .toString()).isEqualTo(distributionUrl);
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX86() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("x86", "Windows NT", new Environment(null, null)), VERSION,
                URL_PATTERN))
            .toString()).isEqualTo("https://foo.bar/v3.5.2-win-x86.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX64() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("x64", "Windows NT", new Environment(null, null)), VERSION,
                URL_PATTERN))
            .toString()).isEqualTo("https://foo.bar/v3.5.2-win-x64.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsLinuxAndJreArchIsAmd64() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("amd64", "Linux", new Environment(null, null)), VERSION,
                URL_PATTERN))
            .toString()).isEqualTo("https://foo.bar/v3.5.2-linux-x64.tar.gz");
    }

    @Test
    void shouldResolveUrlWhenOsIsMacAndJreArchIsPPC() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("ppc", "Mac OS X", new Environment(null, null)), VERSION,
                URL_PATTERN))
            .toString()).isEqualTo("https://foo.bar/v3.5.2-darwin-x64.tar.gz");
    }
}
