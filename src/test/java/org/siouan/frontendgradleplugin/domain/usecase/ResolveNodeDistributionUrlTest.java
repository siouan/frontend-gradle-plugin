package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionUrlTest {

    private static final String VERSION = "3.5.2";

    @InjectMocks
    private ResolveNodeDistributionUrl usecase;

    @Test
    void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl()
        throws UnsupportedPlatformException, MalformedURLException {
        assertThat(
            usecase.execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, null, null))).isNotNull();
    }

    @Test
    void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws UnsupportedPlatformException, MalformedURLException {
        final URL distributionUrl = new URL("http://url");
        assertThat(
            usecase.execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, distributionUrl, null))).
            isEqualTo(distributionUrl);
    }

    @Test
    void shouldReturnDownloadUrlPatternWhenResolvingWithPattern()
        throws UnsupportedPlatformException, MalformedURLException {
        final String pattern = "https://foo.bar/vVERSION-OS_EXTENSION";
        DistributionDefinition distributionDefinition = new DistributionDefinition(new Platform("amd64", "Linux"), VERSION, null, pattern);

        String resolvedUrl = usecase.execute(distributionDefinition).toString();

        assertThat(resolvedUrl).isEqualTo("https://foo.bar/v3.5.2-linux-x64.tar.gz");
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX86() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(new Platform("x86", "Windows NT"), VERSION, null, null))
            .toString()).endsWith("-win-x86.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX64() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(
            usecase.execute(new DistributionDefinition(new Platform("x64", "Windows NT"), VERSION, null,null )).toString()).
            endsWith("-win-x64.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsLinuxAndJreArchIsAmd64() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(
            usecase.execute(new DistributionDefinition(new Platform("amd64", "Linux"), VERSION, null, null)).toString()).
            endsWith("-linux-x64.tar.gz");
    }

    @Test
    void shouldFailWhenOsIsLinuxAndJreArchIsI386() {
        assertThatThrownBy(
            () -> usecase.execute(new DistributionDefinition(new Platform("i386", "Linux"), VERSION, null, null)))
            .isInstanceOf(UnsupportedPlatformException.class);
    }

    @Test
    void shouldResolveUrlWhenOsIsMacAndJreArchIsPPC() throws UnsupportedPlatformException, MalformedURLException {
        assertThat(
            usecase.execute(new DistributionDefinition(new Platform("ppc", "Mac OS X"), VERSION, null, null)).toString()).
            endsWith("-darwin-x64.tar.gz");
    }

    @Test
    void shouldFailWhenOsIsSolarisAndJreArchIsSparc() {
        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(new Platform("sparc", "Solaris"), VERSION, null, null))).isInstanceOf(
            UnsupportedPlatformException.class);
    }
}
