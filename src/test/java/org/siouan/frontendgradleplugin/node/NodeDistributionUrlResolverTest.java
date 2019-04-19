package org.siouan.frontendgradleplugin.node;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;

/**
 * Unit tests for the {@link NodeDistributionUrlResolver} class.
 */
public class NodeDistributionUrlResolverTest {

    private static final String VERSION = "3.5.2";

    @Test
    public void shouldFailWhenBuildingInstanceWithNullVersionAndNullDistributionUrl() {
        assertThatThrownBy(() -> new NodeDistributionUrlResolver(null, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailWhenResolvingWithInvalidDistributionUrl() {
        assertThatThrownBy(() -> new NodeDistributionUrlResolver(null, "fot://greg:://grrg:").resolve())
            .isInstanceOf(DistributionUrlResolverException.class).hasCauseInstanceOf(MalformedURLException.class);
    }

    @Test
    public void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl()
        throws DistributionUrlResolverException {
        assertThat(new NodeDistributionUrlResolver(VERSION, null).resolve()).isNotNull();
    }

    @Test
    public void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws DistributionUrlResolverException {
        final String distributionUrl = "http://url";
        assertThat(new NodeDistributionUrlResolver(null, distributionUrl).resolve().toString())
            .isEqualTo(distributionUrl);
    }

    @Test
    public void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX86() throws DistributionUrlResolverException {
        assertThat(new NodeDistributionUrlResolver(VERSION, null, "Windows NT", "x86").resolve().toString())
            .endsWith("-win-x86.zip");
    }

    @Test
    public void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX64() throws DistributionUrlResolverException {
        assertThat(new NodeDistributionUrlResolver(VERSION, null, "Windows NT", "x64").resolve().toString())
            .endsWith("-win-x64.zip");
    }

    @Test
    public void shouldResolveUrlWhenOsIsLinuxAndJreArchIsAmd64() throws DistributionUrlResolverException {
        assertThat(new NodeDistributionUrlResolver(VERSION, null, "Linux", "amd64").resolve().toString())
            .endsWith("-linux-x64.tar.xz");
    }

    @Test
    public void shouldFailWhenOsIsLinuxAndJreArchIsI386() {
        assertThatThrownBy(() -> new NodeDistributionUrlResolver(VERSION, null, "Linux", "i386").resolve())
            .isInstanceOf(DistributionUrlResolverException.class).hasNoCause();
    }

    @Test
    public void shouldResolveUrlWhenOsIsMacAndJreArchIsPPC() throws DistributionUrlResolverException {
        assertThat(new NodeDistributionUrlResolver(VERSION, null, "Mac OS X", "ppc").resolve().toString())
            .endsWith("-darwin-x64.tar.gz");
    }

    @Test
    public void shouldFailWhenOsIsSolarisAndJreArchIsSparc() {
        assertThatThrownBy(() -> new NodeDistributionUrlResolver(VERSION, null, "Solaris", "sparc").resolve())
            .isInstanceOf(DistributionUrlResolverException.class).hasNoCause();
    }
}
