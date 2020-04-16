package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.Collections.unmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class AbstractGetExecutablePathTest {

    private static final Path WINDOWS_PATH_1 = Paths.get("windows-path-1");

    private static final Path WINDOWS_PATH_2 = Paths.get("windows-path-2");

    private static final Path NON_WINDOWS_PATH_1 = Paths.get("non-windows-path-1");

    private static final Path NON_WINDOWS_PATH_2 = Paths.get("non-windows-path-2");

    private static final Path INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private GetExecutablePathImpl usecase;

    @Test
    void shouldReturnNoExecutableWhenOsIsWindowsAndNoExecutableIsPredefined() {
        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutableWhenOsIsNotWindowsAndNoExecutableIsPredefined() {
        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutableWhenOsIsWindowsAndNoExecutableExists() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_1))).thenReturn(false);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_2))).thenReturn(false);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoExecutableWhenOsIsNotWindowsAndNoExecutableExists() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_1))).thenReturn(false);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_2))).thenReturn(false);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).isEmpty();

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnFirstExecutableWhenFileExistsAndOsIsWindows() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_1))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_1));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnSecondExecutableWhenFileExistsAndOsIsWindows() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_1))).thenReturn(false);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_2))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(WINDOWS_PATH_2));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnFirstExecutableWhenFileExistsAndOsIsNotWindows() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_1))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_1));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnSecondExecutableWhenFileExistsAndOsIsNotWindows() {
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_1);
        usecase.addWindowsRelativeExecutablePath(WINDOWS_PATH_2);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_1);
        usecase.addNonWindowsRelativeExecutablePath(NON_WINDOWS_PATH_2);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_1))).thenReturn(false);
        when(fileManager.exists(INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_2))).thenReturn(true);

        assertThat(usecase.execute(INSTALL_DIRECTORY_PATH, PlatformFixture.ANY_NON_WINDOWS_PLATFORM)).contains(
            INSTALL_DIRECTORY_PATH.resolve(NON_WINDOWS_PATH_2));

        verifyNoMoreInteractions(fileManager);
    }

    private static class GetExecutablePathImpl extends AbstractGetExecutablePath {

        private final List<Path> windowsExecutablePaths;

        private final List<Path> nonWindowsExecutablePaths;

        GetExecutablePathImpl(final FileManager fileManager) {
            super(fileManager);
            this.windowsExecutablePaths = new ArrayList<>();
            this.nonWindowsExecutablePaths = new ArrayList<>();
        }

        void addWindowsRelativeExecutablePath(@Nonnull final Path path) {
            windowsExecutablePaths.add(path);
        }

        void addNonWindowsRelativeExecutablePath(@Nonnull final Path path) {
            nonWindowsExecutablePaths.add(path);
        }

        @Nonnull
        @Override
        protected List<Path> getWindowsRelativeExecutablePaths() {
            return unmodifiableList(windowsExecutablePaths);
        }

        @Nonnull
        @Override
        protected List<Path> getNonWindowsRelativeExecutablePaths() {
            return unmodifiableList(nonWindowsExecutablePaths);
        }
    }
}
