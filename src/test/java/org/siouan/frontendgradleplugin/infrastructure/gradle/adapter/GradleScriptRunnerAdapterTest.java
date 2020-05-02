package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.process.ExecResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.usecase.ResolveExecutionSettings;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.util.ExecSpecActionMatcher;

@ExtendWith(MockitoExtension.class)
class GradleScriptRunnerAdapterTest {

    private static final String SCRIPT = "script";

    @Mock
    private Project project;

    @Mock
    private ResolveExecutionSettings resolveExecutionSettings;

    @InjectMocks
    private GradleScriptRunnerAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new GradleScriptRunnerAdapter(resolveExecutionSettings, mock(Logger.class));
    }

    @Test
    void shouldFailResolvingExecSettingsWhenExecutableIsNotFound() throws ExecutableNotFoundException {
        final ScriptProperties scriptProperties = new ScriptProperties(project,
            PathFixture.ANY_PATH.resolve("frontend"), ExecutableType.NPM, PathFixture.ANY_PATH.resolve("node"),
            PathFixture.ANY_PATH.resolve("yarn"), SCRIPT, PlatformFixture.LOCAL_PLATFORM);
        final ExecutableNotFoundException expectedException = new ExecutableNotFoundException(Paths.get("exe"));
        when(resolveExecutionSettings.execute(scriptProperties.getPackageJsonDirectoryPath(),
            scriptProperties.getExecutableType(), scriptProperties.getNodeInstallDirectory(),
            scriptProperties.getYarnInstallDirectory(), scriptProperties.getPlatform(),
            scriptProperties.getScript())).thenThrow(expectedException);

        assertThatThrownBy(() -> adapter.execute(scriptProperties)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveExecutionSettings, project);
    }

    @Test
    void shouldRunScriptWhenSettingsAreResolved() throws ExecutableNotFoundException {
        final Path nodeInstallationDirectory = PathFixture.ANY_PATH.resolve("node");
        final ScriptProperties scriptProperties = new ScriptProperties(project,
            PathFixture.ANY_PATH.resolve("frontend"), ExecutableType.NPM, nodeInstallationDirectory,
            PathFixture.ANY_PATH.resolve("yarn"), SCRIPT, PlatformFixture.LOCAL_PLATFORM);
        final Set<Path> executablePaths = emptySet();
        final List<String> arguments = emptyList();
        final ExecutionSettings executionSettings = new ExecutionSettings(PathFixture.ANY_PATH.resolve("work"),
            executablePaths, nodeInstallationDirectory.resolve("npm"), arguments);
        when(resolveExecutionSettings.execute(scriptProperties.getPackageJsonDirectoryPath(),
            scriptProperties.getExecutableType(), scriptProperties.getNodeInstallDirectory(),
            scriptProperties.getYarnInstallDirectory(), scriptProperties.getPlatform(),
            scriptProperties.getScript())).thenReturn(executionSettings);
        final ExecResult execResult = mock(ExecResult.class);
        when(project.exec(
            argThat(new ExecSpecActionMatcher(new ExecSpecAction(executionSettings, execSpec -> {}))))).thenReturn(
            execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        adapter.execute(scriptProperties);

        verify(execResult).assertNormalExitValue();
        verifyNoMoreInteractions(resolveExecutionSettings, project, execResult);
    }
}
