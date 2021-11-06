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
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;

@ExtendWith(MockitoExtension.class)
class ResolveExecutionSettingsTest {

    private static final Path PACKAGE_JSON_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("frontend");

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("node");

    private static final String SCRIPT = " run script ";

    @Mock
    private GetNodeExecutablePath getNodeExecutablePath;

    @Mock
    private ResolveExecutablePath resolveExecutablePath;

    @InjectMocks
    private ResolveExecutionSettings usecase;

    @Test
    void shouldFailResolvingExecSettingsWhenNodeExecutableCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("node"));
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH,
                platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldFailResolvingExecSettingsWhenExecutablePathCannotBeFound() throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(NODE_INSTALL_DIRECTORY_PATH.resolve("node"));
        final Exception expectedException = new ExecutableNotFoundException(Paths.get("yarn"));
        when(resolveExecutablePath.execute(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenThrow(expectedException);

        assertThatThrownBy(
            () -> usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH,
                platform, SCRIPT)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeInPathAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(null, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NODE, null, platform)).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                nodeExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNpmInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNpxInDistributionAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npxExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npx");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(npxExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + npxExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenNodeAndYarnExecutablesAreInInstallDirectoryAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + yarnExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenNodeAndYarnExecutablesAreInPathAndOsIsWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = Paths.get("node");
        final Path yarnExecutablePath = Paths.get("yarn");
        when(getNodeExecutablePath.execute(null, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.YARN, null, platform)).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            null, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG,
                yarnExecutablePath + " run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNodeInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpmInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = nodeExecutablePath.resolveSibling("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpxInPathAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npxExecutablePath = nodeExecutablePath.resolveSibling("npx");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.NPX, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(npxExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPX,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + npxExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenNodeAndYarnExecutablesAreInInstallDirectoryAndOsIsNotWindows()
        throws ExecutableNotFoundException {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(resolveExecutablePath.execute(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, platform))
            .thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments())
            .containsExactly(ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG,
                "\"" + yarnExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, resolveExecutablePath);
    }
}
