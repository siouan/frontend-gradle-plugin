package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionUrlTest {

    private static final String VERSION = "3.5.2";

    @InjectMocks
    private ResolveNodeDistributionUrl usecase;

    @Test
    void shouldFailWhenBuildingInstanceWithNullVersionAndNullDistributionUrl() {
        assertThatThrownBy(() -> usecase.execute(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFailWhenResolvingWithInvalidDistributionUrl() {
        assertThatThrownBy(
            () -> new ResolveNodeDistributionUrl().execute(null)/*.execute(null, "fot://greg:://grrg:")*/)
            .isInstanceOf(DistributionUrlResolverException.class)
            .hasCauseInstanceOf(MalformedURLException.class);
    }

    @Test
    void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl() throws DistributionUrlResolverException {
        assertThat(new ResolveNodeDistributionUrl().execute(null)/*.execute(VERSION, null)*/).isNotNull();
    }

    @Test
    void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws DistributionUrlResolverException {
        final String distributionUrl = "http://url";
        assertThat(
            new ResolveNodeDistributionUrl().execute(null)/*.execute(null, distributionUrl)*/.toString()).isEqualTo(
            distributionUrl);
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX86() throws DistributionUrlResolverException {
        assertThat(new ResolveNodeDistributionUrl().execute(
            null)/*.execute(VERSION, null, "Windows NT", "x86")*/.toString()).endsWith("-win-x86.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsWindowsNTAndJreArchIsX64() throws DistributionUrlResolverException {
        assertThat(new ResolveNodeDistributionUrl().execute(
            null)/*.execute(VERSION, null, "Windows NT", "x64")*/.toString()).endsWith("-win-x64.zip");
    }

    @Test
    void shouldResolveUrlWhenOsIsLinuxAndJreArchIsAmd64() throws DistributionUrlResolverException {
        assertThat(new ResolveNodeDistributionUrl().execute(
            null)/*.execute(VERSION, null, "Linux", "amd64")*/.toString()).endsWith("-linux-x64.tar.gz");
    }

    @Test
    void shouldFailWhenOsIsLinuxAndJreArchIsI386() {
        assertThatThrownBy(
            () -> new ResolveNodeDistributionUrl().execute(null)/*.execute(VERSION, null, "Linux", "i386")*/)
            .isInstanceOf(DistributionUrlResolverException.class)
            .hasNoCause();
    }

    @Test
    void shouldResolveUrlWhenOsIsMacAndJreArchIsPPC() throws DistributionUrlResolverException {
        assertThat(new ResolveNodeDistributionUrl().execute(
            null)/*.execute(VERSION, null, "Mac OS X", "ppc")*/.toString()).endsWith("-darwin-x64.tar.gz");
    }

    @Test
    void shouldFailWhenOsIsSolarisAndJreArchIsSparc() {
        assertThatThrownBy(
            () -> new ResolveNodeDistributionUrl().execute(null)/*.execute(VERSION, null, "Solaris", "sparc")*/)
            .isInstanceOf(DistributionUrlResolverException.class)
            .hasNoCause();
    }
}
