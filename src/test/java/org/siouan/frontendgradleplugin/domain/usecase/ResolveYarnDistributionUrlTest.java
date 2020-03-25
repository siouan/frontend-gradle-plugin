package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;

class ResolveYarnDistributionUrlTest {

    @Test
    void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl() throws DistributionUrlResolverException {
        assertThat(new ResolveYarnDistributionUrl().execute(null)/*.execute("3.65.2", null)*/).isNotNull();
    }

    @Test
    void shouldFailWhenDistributionUrlIsInvalid() {
        final String distributionUrl = "siouan://test";
        assertThatThrownBy(() -> new ResolveYarnDistributionUrl().execute(null)/*.execute(null, distributionUrl)*/)
            .isInstanceOf(DistributionUrlResolverException.class).hasCauseInstanceOf(MalformedURLException.class);
    }

    @Test
    void shouldReturnDistributionUrlWhenResolvingWithNoVersionAndDistributionUrl()
        throws DistributionUrlResolverException {
        final String distributionUrl = "http://url";
        assertThat(new ResolveYarnDistributionUrl().execute(null)/*.execute(null, distributionUrl)*/.toString())
            .isEqualTo(distributionUrl);
    }
}
