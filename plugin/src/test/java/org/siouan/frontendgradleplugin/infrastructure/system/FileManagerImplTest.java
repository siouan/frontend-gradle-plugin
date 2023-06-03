package org.siouan.frontendgradleplugin.infrastructure.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemJvmArch;

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
        assertThat(
            fileManager.setFileExecutable(Paths.get("afile"), aPlatform(getSystemJvmArch(), "Windows NT"))).isFalse();
    }

    @Test
    void should_not_touch_file_permissions_when_file_not_found() throws IOException {
        assertThat(fileManager.setFileExecutable(Paths.get("afile"), aPlatform(getSystemJvmArch(), "Linux"))).isFalse();
    }
}
