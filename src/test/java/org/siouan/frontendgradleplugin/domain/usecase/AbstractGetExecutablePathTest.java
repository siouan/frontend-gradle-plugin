package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class AbstractGetExecutablePathTest {

    private static final Path WINDOWS_EXECUTABLE_FILE_PATH = PathFixture.ANY_PATH.resolve("windows-path");

    private static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = PathFixture.ANY_PATH.resolve("non-windows-path");

    private static final Path INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    private GetExecutablePathImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new GetExecutablePathImpl(mock(Logger.class), WINDOWS_EXECUTABLE_FILE_PATH,
            NON_WINDOWS_EXECUTABLE_FILE_PATH);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsWindowsAndExecutableExistsInInstallDirectory() {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEqualTo(
            executablePath);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsNotWindowsAndExecutableExistsInInstallDirectory() {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEqualTo(
            executablePath);
    }

    private static class GetExecutablePathImpl extends AbstractGetExecutablePath {

        private final Path windowsExecutablePath;

        private final Path nonWindowsExecutablePath;

        GetExecutablePathImpl(final Logger logger, final Path windowsExecutablePath,
            final Path nonWindowsExecutablePath) {
            super(logger);
            this.windowsExecutablePath = windowsExecutablePath;
            this.nonWindowsExecutablePath = nonWindowsExecutablePath;
        }

        @Override
        @Nonnull
        protected Path getWindowsRelativeExecutablePath() {
            return windowsExecutablePath;
        }

        @Override
        @Nonnull
        protected Path getNonWindowsRelativeExecutablePath() {
            return nonWindowsExecutablePath;
        }

        @Nonnull
        @Override
        protected Path getWindowsExecutableFileName() {
            return windowsExecutablePath.getFileName();
        }

        @Nonnull
        @Override
        protected Path getNonWindowsExecutableFileName() {
            return nonWindowsExecutablePath.getFileName();
        }
    }
}
