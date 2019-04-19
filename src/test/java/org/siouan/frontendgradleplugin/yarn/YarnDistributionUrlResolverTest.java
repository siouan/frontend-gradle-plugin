package org.siouan.frontendgradleplugin.yarn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;

/**
 * Unit tests for the {@link YarnDistributionUrlResolver} class.
 */
public class YarnDistributionUrlResolverTest {

    @Test
    public void shouldFailWhenBuildingInstanceWithNullVersionAndNullDistributionUrl() {
        assertThatThrownBy(() -> new YarnDistributionUrlResolver(null, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl()
        throws DistributionUrlResolverException {
        assertThat(new YarnDistributionUrlResolver("3.65.2", null).resolve()).isNotNull();
    }

    @Test
    public void shouldFailWhenDistributionUrlIsInvalid() {
        final String distributionUrl = "siouan://test";
        assertThatThrownBy(() -> new YarnDistributionUrlResolver(null, distributionUrl).resolve().toString())
            .isInstanceOf(DistributionUrlResolverException.class).hasCauseInstanceOf(MalformedURLException.class);
    }

    @Test
    public void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws DistributionUrlResolverException {
        final String distributionUrl = "http://url";
        assertThat(new YarnDistributionUrlResolver(null, distributionUrl).resolve().toString())
            .isEqualTo(distributionUrl);
    }
}
