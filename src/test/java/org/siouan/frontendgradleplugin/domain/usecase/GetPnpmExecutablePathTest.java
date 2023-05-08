package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

@ExtendWith(MockitoExtension.class)
class GetPnpmExecutablePathTest {

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private GetPnpmExecutablePath usecase;

    @Test
    void should_return_relative_executable_path_when_os_is_windows() {
        assertThat(usecase.getWindowsRelativeExecutablePath()).isEqualTo(Paths.get("pnpm.cmd"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_relative_executable_path_when_os_is_not_windows() {
        assertThat(usecase.getNonWindowsRelativeExecutablePath()).isEqualTo(Paths.get("bin", "pnpm"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_executable_file_name_when_os_is_windows() {
        assertThat(usecase.getWindowsExecutableFileName()).isEqualTo(Paths.get("pnpm.cmd"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_executable_file_name_when_os_is_not_windows() {
        assertThat(usecase.getNonWindowsExecutableFileName()).isEqualTo(Paths.get("pnpm"));

        verifyNoMoreInteractions(fileManager);
    }
}