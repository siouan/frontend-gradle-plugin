package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DistributionId;

@ExtendWith(MockitoExtension.class)
class GetDistributionValidatorTest {

    @InjectMocks
    private GetDistributionValidator usecase;

    @Test
    void shouldReturnNodeDistributionValidator() {
        assertThat(usecase.execute(DistributionId.NODE)).containsInstanceOf(ValidateNodeDistribution.class);
    }

    @Test
    void shouldReturnNoDistributionValidator() {
        assertThat(usecase.execute(DistributionId.YARN)).isEmpty();
    }
}
