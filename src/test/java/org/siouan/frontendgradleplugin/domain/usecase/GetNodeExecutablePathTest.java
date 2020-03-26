package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class GetNodeExecutablePathTest {

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private GetNodeExecutablePath usecase;

    @Test
    void shouldReturnNoExecutableWhenNotFound() {
        when(fileManager.exists(argThat(path -> path.startsWith(NODE_INSTALL_DIRECTORY_PATH)))).thenReturn(false);

        assertThat(usecase.execute(NODE_INSTALL_DIRECTORY_PATH, PlatformFixture.LOCAL_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnAnyExecutableWhenFileExistsAndOsIsWindows() {
        when(fileManager.exists(argThat(path -> path.startsWith(NODE_INSTALL_DIRECTORY_PATH)))).thenReturn(true);

        assertThat(
            usecase.execute(NODE_INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).hasValueSatisfying(
            new Condition<>(executablePath -> GetNodeExecutablePath.WINDOWS_EXECUTABLE_PATHS
                .stream()
                .anyMatch(windowsExecutablePath -> NODE_INSTALL_DIRECTORY_PATH
                    .resolve(windowsExecutablePath)
                    .equals(executablePath)), null));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableWhenFileExistsAndOsIsNotWindows() {
        when(fileManager.exists(argThat(path -> path.startsWith(NODE_INSTALL_DIRECTORY_PATH)))).thenReturn(true);

        assertThat(
            usecase.execute(NODE_INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).hasValueSatisfying(
            executablePath -> assertThat(executablePath).isEqualTo(
                NODE_INSTALL_DIRECTORY_PATH.resolve(GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_PATH)));

        verifyNoMoreInteractions(fileManager);
    }
}
