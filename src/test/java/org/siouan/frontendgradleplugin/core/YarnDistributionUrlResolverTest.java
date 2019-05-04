package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link YarnDistributionUrlResolver} class.
 */
class YarnDistributionUrlResolverTest {

    @Test
    void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl() throws DistributionUrlResolverException {
        assertThat(new YarnDistributionUrlResolver("3.65.2", null).resolve()).isNotNull();
    }

    @Test
    void shouldFailWhenDistributionUrlIsInvalid() {
        final String distributionUrl = "siouan://test";
        assertThatThrownBy(() -> new YarnDistributionUrlResolver(null, distributionUrl).resolve())
            .isInstanceOf(DistributionUrlResolverException.class).hasCauseInstanceOf(MalformedURLException.class);
    }

    @Test
    void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws DistributionUrlResolverException {
        final String distributionUrl = "http://url";
        assertThat(new YarnDistributionUrlResolver(null, distributionUrl).resolve().toString())
            .isEqualTo(distributionUrl);
    }
}
