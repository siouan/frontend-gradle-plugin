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
    void shouldFailResolvingExecSettingsWhenNodeExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("node"));
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenNpmExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            NODE_INSTALL_DIRECTORY_PATH.resolve("node"));
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("npm"));
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenNpxExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            NODE_INSTALL_DIRECTORY_PATH.resolve("node"));
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("npx"));
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, null,
                platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenYarnExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            NODE_INSTALL_DIRECTORY_PATH.resolve("node"));
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("yarn"));
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH,
                YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenExecutableTypeIsUnknown() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(
            NODE_INSTALL_DIRECTORY_PATH.resolve("node"));

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, "JAVAC", NODE_INSTALL_DIRECTORY_PATH, null, platform,
                SCRIPT)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeInPathAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(null, platform)).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            null, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, nodeExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNpmInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNpxInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npxExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npx");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(npxExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npxExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsYarnInDistributionAndNodeIsInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = YARN_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + yarnExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsYarnInPathAndNodeIsInPathAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT", null, null);
        final Path nodeExecutablePath = Paths.get("node");
        final Path yarnExecutablePath = Paths.get("yarn");
        when(getNodeExecutablePath.execute(null, platform)).thenReturn(nodeExecutablePath);
        when(getYarnExecutablePath.execute(null, platform)).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            null, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, yarnExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNodeInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, nodeExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpmInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = nodeExecutablePath.resolveSibling("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getNpmExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, npmExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpxInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npxExecutablePath = nodeExecutablePath.resolveSibling("npx");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getNpxExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(npxExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX,
            NODE_INSTALL_DIRECTORY_PATH, null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, npxExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsYarnInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), "Linux", null, null);
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = YARN_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getYarnExecutablePath.execute(YARN_INSTALL_DIRECTORY_PATH, platform)).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, YARN_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, yarnExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getNpmExecutablePath, getNpxExecutablePath,
            getYarnExecutablePath);
    }
}
