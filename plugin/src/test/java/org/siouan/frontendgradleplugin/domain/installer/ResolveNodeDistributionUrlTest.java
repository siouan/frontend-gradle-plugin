package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatform;

import java.net.MalformedURLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;

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
    void should_return_same_url_if_it_does_not_contain_any_token()
        throws MalformedURLException, UnsupportedPlatformException {
        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(aPlatform(), "3.5.2", "https://foo.bar/dist/",
            "node.zip"))).hasToString("https://foo.bar/dist/node.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void should_replace_version_token_when_present() throws MalformedURLException, UnsupportedPlatformException {
        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(aPlatform(), "3.5.2", "https://foo.bar/dist/",
            "node-vVERSION.zip"))).hasToString("https://foo.bar/dist/node-v3.5.2.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void should_fail_replacing_arch_token_when_present_and_platform_is_unsupported() {
        final Platform platform = aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usecase.execute(
            new ResolveNodeDistributionUrlCommand(platform, "3.5.2", "https://foo.bar/dist/",
                "node-ARCH.zip"))).isInstanceOf(UnsupportedPlatformException.class);

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void should_replace_arch_token_when_present_and_platform_is_supported()
        throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("linux-x64"));

        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(platform, "3.5.2", "https://foo.bar/dist/",
            "node-ARCH.zip"))).hasToString("https://foo.bar/dist/node-linux-x64.zip");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void should_replace_type_token_when_present() throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = aPlatform();
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(platform, "3.5.2", "https://foo.bar/dist/",
            "node-dist.TYPE"))).hasToString("https://foo.bar/dist/node-dist.tar.gz");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }

    @Test
    void should_return_full_download_url_when_resolving_url_with_mac_os_and_jvm_arch_is_aarch_64()
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = aPlatform("aarch64", "Mac OS X");
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("darwin-arm64"));
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(platform, VERSION, "https://foo.bar/dist/",
            "node-vVERSION-ARCH.TYPE"))).hasToString("https://foo.bar/dist/node-v3.5.2-darwin-arm64.tar.gz");
    }

    @Test
    void should_replace_all_tokens_when_present_and_platform_is_supported()
        throws MalformedURLException, UnsupportedPlatformException {
        final Platform platform = aPlatform();
        when(resolveNodeDistributionArchitectureId.execute(platform)).thenReturn(Optional.of("linux-x64"));
        when(resolveNodeDistributionType.execute(platform)).thenReturn("tar.gz");

        assertThat(usecase.execute(new ResolveNodeDistributionUrlCommand(platform, "3.5.2", "https://foo.bar/dist/",
            "node-vVERSION-ARCH.TYPE"))).hasToString("https://foo.bar/dist/node-v3.5.2-linux-x64.tar.gz");

        verifyNoMoreInteractions(resolveNodeDistributionArchitectureId, resolveNodeDistributionType);
    }
}
