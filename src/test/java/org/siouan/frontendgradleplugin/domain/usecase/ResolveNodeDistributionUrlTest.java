package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionUrlTest {

    private static final String VERSION = "3.5.2";

    @Mock
    private ResolveNodeDistributionArchitectureId resolveNodeDistributionArchitectureId;

    @Mock
    private ResolveNodeDistributionType resolveNodeDistributionType;

    @InjectMocks
    private ResolveNodeDistributionUrl usecase;

    @Test
    void shouldReturnSameUrlIfItDoesNotContainAnyToken() throws MalformedURLException, UnsupportedPlatformException {
        assertThat(usecase.execute(
            new DistributionDefinition(PlatformFixture.aPlatform(), "3.5.2", "https://foo.bar/dist/", "node.zip")))
            .hasToString("https://foo.bar/dist/node.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void shouldReplaceVersionTokenWhenPresent() throws MalformedURLException, UnsupportedPlatformException {
        assertThat(usecase.execute(
            new DistributionDefinition(PlatformFixture.aPlatform(), "3.5.2", "https://foo.bar/dist/",
                "node-vVERSION.zip"))).hasToString("https://foo.bar/dist/node-v3.5.2.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void shouldFailReplacingArchTokenWhenPresentAndPlatformIsUnsupported() {
        final Platform platform = PlatformFixture.aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usecase.execute(
            new DistributionDefinition(platform, "3.5.2", "https://foo.bar/dist/", "node-ARCH.zip")))
            .isInstanceOf(UnsupportedPlatformException.class);

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void shouldReplaceArchTokenWhenPresentAndPlatformIsSupported()
        throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = PlatformFixture.aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("linux-x64"));

        assertThat(
            usecase.execute(new DistributionDefinition(platform, "3.5.2", "https://foo.bar/dist/", "node-ARCH.zip")))
            .hasToString("https://foo.bar/dist/node-linux-x64.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void shouldReplaceTypeTokenWhenPresent() throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = PlatformFixture.aPlatform();
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(
            usecase.execute(new DistributionDefinition(platform, "3.5.2", "https://foo.bar/dist/", "node-dist.TYPE")))
            .hasToString("https://foo.bar/dist/node-dist.tar.gz");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void shouldReturnFullDownloadUrlWhenResolvingUrlWithMacOsAndJvmArchIsAarch64()
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = PlatformFixture.aPlatform("aarch64", "Mac OS X");
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("darwin-arm64"));
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(usecase
            .execute(new DistributionDefinition(platform, VERSION, "https://foo.bar/dist/", "node-vVERSION-ARCH.TYPE"))
            .toString()).isEqualTo("https://foo.bar/dist/node-v3.5.2-darwin-arm64.tar.gz");
    }

    @Test
    void shouldReplaceAllTokensWhenPresentAndPlatformIsSupported()
        throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = PlatformFixture.aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("linux-x64"));
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(usecase.execute(
            new DistributionDefinition(platform, "3.5.2", "https://foo.bar/dist/", "node-vVERSION-ARCH.TYPE")))
            .hasToString("https://foo.bar/dist/node-v3.5.2-linux-x64.tar.gz");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }
}
