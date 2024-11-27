package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.siouan.frontendgradleplugin.test.PathFixture.ANY_PATH;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.ANY_NON_WINDOWS_PLATFORM;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.ANY_WINDOWS_PLATFORM;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractResolveExecutablePathTest {

    private static final Path WINDOWS_EXECUTABLE_FILE_PATH = ANY_PATH.resolve("windows-path");

    private static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = ANY_PATH.resolve("non-windows-path");

    private static final Path INSTALL_DIRECTORY_PATH = ANY_PATH;

    private ResolveExecutablePathImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new ResolveExecutablePathImpl(mock(Logger.class), WINDOWS_EXECUTABLE_FILE_PATH,
            NON_WINDOWS_EXECUTABLE_FILE_PATH);
    }

    @Test
    void should_return_executable_path_when_os_is_windows_and_executable_exists_in_install_directory() {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH);

        assertThat(usecase.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(INSTALL_DIRECTORY_PATH)
            .platform(ANY_WINDOWS_PLATFORM)
            .build())).isEqualTo(executablePath);
    }

    @Test
    void should_return_executable_path_when_os_is_not_windows_and_executable_exists_in_install_directory() {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH);

        assertThat(usecase.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(INSTALL_DIRECTORY_PATH)
            .platform(ANY_NON_WINDOWS_PLATFORM)
            .build())).isEqualTo(executablePath);
    }

    private static class ResolveExecutablePathImpl extends AbstractResolveExecutablePath {

        private final Path windowsExecutablePath;

        private final Path nonWindowsExecutablePath;

        ResolveExecutablePathImpl(final Logger logger, final Path windowsExecutablePath,
            final Path nonWindowsExecutablePath) {
            super(logger);
            this.windowsExecutablePath = windowsExecutablePath;
            this.nonWindowsExecutablePath = nonWindowsExecutablePath;
        }

        @Override
        protected Path getWindowsRelativeExecutablePath() {
            return windowsExecutablePath;
        }

        @Override
        protected Path getNonWindowsRelativeExecutablePath() {
            return nonWindowsExecutablePath;
        }

        @Override
        protected String getWindowsExecutableFileName() {
            return windowsExecutablePath.getFileName().toString();
        }

        @Override
        protected String getNonWindowsExecutableFileName() {
            return nonWindowsExecutablePath.getFileName().toString();
        }
    }
}
