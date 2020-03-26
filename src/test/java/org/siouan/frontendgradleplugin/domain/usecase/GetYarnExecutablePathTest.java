package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class GetYarnExecutablePathTest {

    private static final Path YARN_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private GetYarnExecutablePath usecase;

    @Test
    void shouldReturnNoExecutableWhenNotFound() {
        when(fileManager.exists(argThat(path -> path.startsWith(YARN_INSTALL_DIRECTORY_PATH)))).thenReturn(false);

        assertThat(usecase.execute(YARN_INSTALL_DIRECTORY_PATH, PlatformFixture.LOCAL_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableWhenFileExistsAndOsIsWindows() {
        when(fileManager.exists(argThat(path -> path.startsWith(YARN_INSTALL_DIRECTORY_PATH)))).thenReturn(true);

        assertThat(
            usecase.execute(YARN_INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).hasValueSatisfying(
            executablePath -> assertThat(executablePath).isEqualTo(
                YARN_INSTALL_DIRECTORY_PATH.resolve(GetYarnExecutablePath.WINDOWS_EXECUTABLE_PATH)));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableWhenFileExistsAndOsIsNotWindows() {
        when(fileManager.exists(argThat(path -> path.startsWith(YARN_INSTALL_DIRECTORY_PATH)))).thenReturn(true);

        assertThat(
            usecase.execute(YARN_INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).hasValueSatisfying(
            executablePath -> assertThat(executablePath).isEqualTo(
                YARN_INSTALL_DIRECTORY_PATH.resolve(GetYarnExecutablePath.NON_WINDOWS_EXECUTABLE_PATH)));

        verifyNoMoreInteractions(fileManager);
    }
}
