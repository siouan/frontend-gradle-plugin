package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetNodeExecutablePathTest {

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ResolveGlobalNodeExecutablePath usecase;

    @Test
    void should_return_relative_executable_path_when_os_is_windows() {
        assertThat(usecase.getWindowsRelativeExecutablePath()).isEqualTo(Paths.get("node.exe"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_relative_executable_path_when_os_is_not_windows() {
        assertThat(usecase.getNonWindowsRelativeExecutablePath()).isEqualTo(Paths.get("bin", "node"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_executable_file_name_when_os_is_windows() {
        assertThat(usecase.getWindowsExecutableFileName()).isEqualTo("node.exe");

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_executable_file_name_when_os_is_not_windows() {
        assertThat(usecase.getNonWindowsExecutableFileName()).isEqualTo("node");

        verifyNoMoreInteractions(fileManager);
    }
}
