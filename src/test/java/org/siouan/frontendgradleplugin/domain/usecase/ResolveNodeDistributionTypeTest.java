package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionTypeTest {

    @InjectMocks
    private ResolveNodeDistributionType usecase;

    @Test
    void shouldReturnZipWhenOsIsWindows() {
        assertThat(usecase.execute(PlatformFixture.ANY_WINDOWS_PLATFORM)).isEqualTo(
            ResolveNodeDistributionType.ZIP_TYPE);
    }

    @Test
    void shouldReturnTarGzWhenOsIsNotWindows() {
        assertThat(usecase.execute(PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEqualTo(
            ResolveNodeDistributionType.TAR_GZ_TYPE);
    }
}
