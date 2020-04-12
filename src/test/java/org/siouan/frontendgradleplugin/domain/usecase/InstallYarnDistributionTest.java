package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DistributionId;

@ExtendWith(MockitoExtension.class)
class InstallYarnDistributionTest {

    @Mock
    private GetDistribution getDistribution;

    @Mock
    private DeployDistribution deployDistribution;

    @InjectMocks
    private InstallYarnDistribution usecase;

    @Test
    void shouldReturnNodeDistributionId() {
        assertThat(usecase.getDistributionId()).isEqualTo(DistributionId.YARN);
    }
}
