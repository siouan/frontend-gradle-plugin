package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class GetExecutablePathTest {

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("node");

    @Mock
    private GetCorepackExecutablePath getCorepackExecutablePath;

    @Mock
    private GetNodeExecutablePath getNodeExecutablePath;

    @Mock
    private GetNpmExecutablePath getNpmExecutablePath;

    @Mock
    private GetPnpmExecutablePath getPnpmExecutablePath;

    @Mock
    private GetYarnExecutablePath getYarnExecutablePath;

    @InjectMocks
    private GetExecutablePath usecase;

    @Test
    void shouldResolveNodeExecutablePath() {
        final Platform platform = PlatformFixture.aPlatform();
        final Path executablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathQuery(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_corepack_executable_path() {
        final Platform platform = PlatformFixture.aPlatform();
        final Path executablePath = Paths.get("corepack");
        when(getCorepackExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathQuery(ExecutableType.COREPACK, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void shouldResolveNpmExecutablePath() {
        final Platform platform = PlatformFixture.aPlatform();
        final Path executablePath = Paths.get("npm");
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathQuery(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void shouldResolvePnpmExecutablePath() {
        final Platform platform = PlatformFixture.aPlatform();
        final Path executablePath = Paths.get("pnpm");
        when(getPnpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathQuery(ExecutableType.PNPM, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void shouldResolveYarnExecutablePath() {
        final Platform platform = PlatformFixture.aPlatform();
        final Path executablePath = Paths.get("yarn");
        when(getYarnExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathQuery(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }
}
