package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.ANY_NON_WINDOWS_PLATFORM;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.ANY_WINDOWS_PLATFORM;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveNodeDistributionTypeTest {

    @InjectMocks
    private ResolveNodeDistributionType usecase;

    @Test
    void should_return_zip_when_os_is_windows() {
        assertThat(usecase.execute(ANY_WINDOWS_PLATFORM)).isEqualTo(ResolveNodeDistributionType.ZIP_TYPE);
    }

    @Test
    void should_return_tar_gz_when_os_is_not_windows() {
        assertThat(usecase.execute(ANY_NON_WINDOWS_PLATFORM)).isEqualTo(ResolveNodeDistributionType.TAR_GZ_TYPE);
    }
}
