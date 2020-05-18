package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class AbstractGetExecutablePathTest {

    private static final Path WINDOWS_EXECUTABLE_FILE_PATH = PathFixture.ANY_PATH.resolve("windows-path");

    private static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = PathFixture.ANY_PATH.resolve("non-windows-path");

    private static final Path ENVIRONMENT_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("install");

    private static final Path INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    @Mock
    private FileManager fileManager;

    private GetExecutablePathImpl usecase;

    @BeforeEach
    void setUp() {
        usecase = new GetExecutablePathImpl(fileManager, mock(Logger.class), WINDOWS_EXECUTABLE_FILE_PATH,
            NON_WINDOWS_EXECUTABLE_FILE_PATH);
    }

    @Test
    void shouldReturnNoExecutablePathWhenOsIsWindowsAndExecutableDoesNotExistInInstallDirectory() {
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH))).thenReturn(false);

        assertThatThrownBy(
            () -> usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isInstanceOf(
            ExecutableNotFoundException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutablePathWhenOsIsWindowsAndExecutableDoesNotExistInEnvironmentInstallDirectory() {
        usecase.init(ENVIRONMENT_INSTALL_DIRECTORY_PATH);
        when(fileManager.exists(ENVIRONMENT_INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH))).thenReturn(
            false);

        assertThatThrownBy(() -> usecase.execute(null, PlatformFixture.ANY_WINDOWS_PLATFORM)).isInstanceOf(
            ExecutableNotFoundException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableFileNameWhenOsIsWindowsAndNoInstallDirectoryIsProvided()
        throws ExecutableNotFoundException {
        assertThat(usecase.execute(null, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEqualTo(
            WINDOWS_EXECUTABLE_FILE_PATH.getFileName());

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsWindowsAndExecutableExistsInInstallDirectory()
        throws ExecutableNotFoundException {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH);
        when(fileManager.exists(executablePath)).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsWindowsAndExecutableExistsInEnvironmentInstallDirectory()
        throws ExecutableNotFoundException {
        usecase.init(ENVIRONMENT_INSTALL_DIRECTORY_PATH);
        final Path executablePath = ENVIRONMENT_INSTALL_DIRECTORY_PATH.resolve(WINDOWS_EXECUTABLE_FILE_PATH);
        when(fileManager.exists(executablePath)).thenReturn(true);

        assertThat(usecase.execute(null, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEqualTo(executablePath);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutablePathWhenOsIsNotWindowsAndExecutableDoesNotExistInInstallDirectory() {
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH))).thenReturn(false);

        assertThatThrownBy(
            () -> usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isInstanceOf(
            ExecutableNotFoundException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutablePathWhenOsIsNotWindowsAndExecutableDoesNotExistInEnvironmentInstallDirectory() {
        usecase.init(ENVIRONMENT_INSTALL_DIRECTORY_PATH);
        when(fileManager.exists(
            ENVIRONMENT_INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH))).thenReturn(false);

        assertThatThrownBy(() -> usecase.execute(null, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isInstanceOf(
            ExecutableNotFoundException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableFileNameWhenOsIsNotWindowsAndNoInstallDirectoryIsProvided()
        throws ExecutableNotFoundException {
        assertThat(usecase.execute(null, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEqualTo(
            NON_WINDOWS_EXECUTABLE_FILE_PATH.getFileName());

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsNotWindowsAndExecutableExistsInInstallDirectory()
        throws ExecutableNotFoundException {
        final Path executablePath = INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH);
        when(fileManager.exists(executablePath)).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutablePathWhenOsIsNotWindowsAndExecutableExistsInEnvironmentInstallDirectory()
        throws ExecutableNotFoundException {
        usecase.init(ENVIRONMENT_INSTALL_DIRECTORY_PATH);
        final Path executablePath = ENVIRONMENT_INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_EXECUTABLE_FILE_PATH);
        when(fileManager.exists(executablePath)).thenReturn(true);

        assertThat(usecase.execute(null, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEqualTo(executablePath);

        verifyNoMoreInteractions(fileManager);
    }

    private static class GetExecutablePathImpl extends AbstractGetExecutablePath {

        private final Path windowsExecutablePath;

        private final Path nonWindowsExecutablePath;

        private Path executablePathFromEnvironment;

        GetExecutablePathImpl(final FileManager fileManager, final Logger logger, final Path windowsExecutablePath,
            final Path nonWindowsExecutablePath) {
            super(fileManager, logger);
            this.windowsExecutablePath = windowsExecutablePath;
            this.nonWindowsExecutablePath = nonWindowsExecutablePath;
        }

        public void init(@Nullable final Path executablePathFromEnvironment) {
            this.executablePathFromEnvironment = executablePathFromEnvironment;
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

        @Nonnull
        @Override
        protected Optional<Path> getInstallDirectoryFromEnvironment(@Nonnull Platform platform) {
            return Optional.ofNullable(executablePathFromEnvironment);
        }
    }
}
