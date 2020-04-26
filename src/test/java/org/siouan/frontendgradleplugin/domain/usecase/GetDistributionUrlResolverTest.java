package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DistributionId;

@ExtendWith(MockitoExtension.class)
class GetDistributionUrlResolverTest {

    @InjectMocks
    private GetDistributionUrlResolver usecase;

    @Test
    void shouldReturnNoResolverWhenDistributionIdIsUnknown() {
        assertThat(usecase.execute("JDK")).isEmpty();
    }

    @Test
    void shouldReturnNodeDistributionUrlResolver() {
        assertThat(usecase.execute(DistributionId.NODE)).containsInstanceOf(ResolveNodeDistributionUrl.class);
    }

    @Test
    void shouldReturnYarnDistributionUrlResolver() {
        assertThat(usecase.execute(DistributionId.YARN)).containsInstanceOf(ResolveYarnDistributionUrl.class);
    }
}
