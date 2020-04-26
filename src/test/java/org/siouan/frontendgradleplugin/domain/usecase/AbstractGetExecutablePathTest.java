package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class AbstractGetExecutablePathTest {

    private static final Path WINDOWS_PATH = Paths.get("windows-path");

    private static final Path NON_WINDOWS_PATH = Paths.get("non-windows-path");

    private static final Path INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    @Mock
    private FileManager fileManager;

    private AbstractGetExecutablePath usecase;

    @Test
    void shouldReturnNoExecutableWhenOsIsWindowsAndExecutableDoesNotExist() {
        usecase = new GetExecutablePathImpl(fileManager, WINDOWS_PATH, NON_WINDOWS_PATH);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH))).thenReturn(false);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutableWhenOsIsNotWindowsAndExecutableDoesNotExist() {
        usecase = new GetExecutablePathImpl(fileManager, WINDOWS_PATH, NON_WINDOWS_PATH);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH))).thenReturn(false);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableWhenOsIsWindowsAndFileExists() {
        usecase = new GetExecutablePathImpl(fileManager, WINDOWS_PATH, NON_WINDOWS_PATH);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnExecutableWhenOsIsNotWindowsAndFileExists() {
        usecase = new GetExecutablePathImpl(fileManager, WINDOWS_PATH, NON_WINDOWS_PATH);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH));

        verifyNoMoreInteractions(fileManager);
    }

    private static class GetExecutablePathImpl extends AbstractGetExecutablePath {

        private final Path windowsExecutablePath;

        private final Path nonWindowsExecutablePath;

        GetExecutablePathImpl(final FileManager fileManager, final Path windowsExecutablePath,
            final Path nonWindowsExecutablePath) {
            super(fileManager);
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
    }
}
