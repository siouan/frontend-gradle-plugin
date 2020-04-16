package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolveExecutionSettingsTest {

    private static final Path PACKAGE_JSON_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("frontend");

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("node");

    private static final Path YARN_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("yarn");

    private static final String SCRIPT = " run script ";

    @Mock
    private GetNodeExecutablePath getNodeExecutablePath;

    @Mock
    private GetNpmExecutablePath getNpmExecutablePath;

    @Mock
    private GetNpxExecutablePath getNpxExecutablePath;

    @Mock
    private GetYarnExecutablePath getYarnExecutablePath;

    @InjectMocks
    private ResolveExecutionSettings usecase;

    @Test
    void shouldFailResolvingExecSettingsWhenNodeExecutableCannotBeFound() {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableType.NODE.name());

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenNpmExecutableCannotBeFound() {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(NODE_INSTALL_DIRECTORY_PATH.resolve("node")));
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableType.NPM.name());

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenNpxExecutableCannotBeFound() {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(NODE_INSTALL_DIRECTORY_PATH.resolve("node")));
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableType.NPX.name());

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenYarnExecutableCannotBeFound() {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(NODE_INSTALL_DIRECTORY_PATH.resolve("node")));
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH,
                YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableType.YARN.name());

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
            String.join(" ", '"' + nodeExecutablePath.toString() + '"', SCRIPT.trim()));
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNpmAndOsIsWindows() throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(npmExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
            String.join(" ", '"' + npmExecutablePath.toString() + '"', SCRIPT.trim()));
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsYarnAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = YARN_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(yarnExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent(),
            yarnExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
            String.join(" ", '"' + yarnExecutablePath.toString() + '"', SCRIPT.trim()));
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNodeAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(nodeExecutablePath);
        assertThat(executionSettings.getArguments()).containsExactly("run", "script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpmAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(npmExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(npmExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(npmExecutablePath);
        assertThat(executionSettings.getArguments()).containsExactly("run", "script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsYarnAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = YARN_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(nodeExecutablePath));
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            Optional.of(yarnExecutablePath));

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent(),
            yarnExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(yarnExecutablePath);
        assertThat(executionSettings.getArguments()).containsExactly("run", "script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }
}
