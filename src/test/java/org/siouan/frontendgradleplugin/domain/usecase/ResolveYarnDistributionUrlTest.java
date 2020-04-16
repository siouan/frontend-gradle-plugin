package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveYarnDistributionUrlTest {

    private static final String VERSION = "3.65.2";

    @InjectMocks
    private ResolveYarnDistributionUrl usecase;

    @Test
    void shouldResolveAndReturnUrlWhenPredefinedUrlIsNotDefined() throws MalformedURLException {
        assertThat(
            usecase.execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, null))).isNotNull();
    }

    @Test
    void shouldReturnDefaultUrlWhenResolvingWithVersionAndNoDistributionUrl() throws MalformedURLException {
        final URL distributionUrl = new URL("http://url");
        assertThat(usecase.execute(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, distributionUrl))).isEqualTo(
            distributionUrl);
    }
}
