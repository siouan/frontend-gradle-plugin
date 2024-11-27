package org.siouan.frontendgradleplugin.infrastructure.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.ANY_NON_WINDOWS_PLATFORM;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.ANY_WINDOWS_PLATFORM;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileManagerImplTest {

    @InjectMocks
    private FileManagerImpl fileManager;

    @Test
    void should_not_touch_file_permissions_when_os_is_windows() throws IOException {
        assertThat(fileManager.setFileExecutable(Paths.get("afile"), ANY_WINDOWS_PLATFORM)).isFalse();
    }

    @Test
    void should_not_touch_file_permissions_when_os_is_not_windows_and_file_does_not_exist() throws IOException {
        assertThat(fileManager.setFileExecutable(Paths.get("afile"), ANY_NON_WINDOWS_PLATFORM)).isFalse();
    }
}
