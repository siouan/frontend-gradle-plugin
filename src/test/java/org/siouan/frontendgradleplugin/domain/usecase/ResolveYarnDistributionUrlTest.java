package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveYarnDistributionUrlTest {

    private static final String URL_PATTERN = "https://foo.bar/vVERSION.tar.gz";

    private static final String VERSION = "3.65.2";

    @InjectMocks
    private ResolveYarnDistributionUrl usecase;

    @Test
    void shouldReturnDownloadUrlPatternWhenResolvingWithPattern() throws MalformedURLException {
        assertThat(usecase
            .execute(new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, URL_PATTERN))
            .toString()).isEqualTo("https://foo.bar/v3.65.2.tar.gz");
    }
}
