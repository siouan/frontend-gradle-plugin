package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveExecutablePathTest {

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("node");

    private static final Path YARN_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("yarn");

    @Mock
    private GetNodeExecutablePath getNodeExecutablePath;

    @Mock
    private GetNpmExecutablePath getNpmExecutablePath;

    @Mock
    private GetNpxExecutablePath getNpxExecutablePath;

    @Mock
    private GetYarnExecutablePath getYarnExecutablePath;

    @InjectMocks
    private ResolveExecutablePath usecase;

    @Test
    void shouldFailResolvingExecutablePathWhenNodeExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("node"));
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecutablePathWhenNpmExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("npm"));
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecutablePathWhenNpxExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("npx"));
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecutablePathWhenYarnExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("yarn"));
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH,
                platform)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveNodeExecutablePath() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Path nodeExecutablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);

        assertThat(usecase.execute(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            nodeExecutablePath);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveNpmExecutablePath() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Path nodeExecutablePath = Paths.get("node");
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);

        assertThat(usecase.execute(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            nodeExecutablePath);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveNpxExecutablePath() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Path nodeExecutablePath = Paths.get("node");
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);

        assertThat(usecase.execute(ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, null, platform)).isEqualTo(
            nodeExecutablePath);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveYarnExecutablePath() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform();
        final Path yarnExecutablePath = Paths.get("yarn");
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(yarnExecutablePath);

        assertThat(usecase.execute(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH,
            platform)).isEqualTo(yarnExecutablePath);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecutablePathWhenExecutableTypeIsUnknown() {
        final Platform platform = PlatformFixture.aPlatform();

        assertThatThrownBy(() -> usecase.execute("JAVAC", NODE_INSTALL_DIRECTORY_PATH, null, platform)).isInstanceOf(
            IllegalArgumentException.class);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }
}
