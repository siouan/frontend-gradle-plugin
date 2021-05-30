package org.siouan.frontendgradleplugin.infrastructure.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class FileManagerImplTest {

    @InjectMocks
    private FileManagerImpl fileProvider;

    @Test
    void shouldNotTouchFilePermissionsWhenOsIsWindows() throws IOException {
        assertThat(fileProvider.setFileExecutable(Paths.get("afile"),
            PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT"))).isFalse();
    }

    @Test
    void shouldNotTouchFilePermissionsWhenFileNotFound() throws IOException {
        assertThat(fileProvider.setFileExecutable(Paths.get("afile"),
            PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux"))).isFalse();
    }
}
