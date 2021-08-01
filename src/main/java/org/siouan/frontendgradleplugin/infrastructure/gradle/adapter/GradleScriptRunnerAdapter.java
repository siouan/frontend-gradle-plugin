package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import javax.annotation.Nonnull;

import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.usecase.ResolveExecutionSettings;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;

/**
 * An adapter that delegates running a script to the executor available in a Gradle project.
 *
 * @since 2.0.0
 */
public class GradleScriptRunnerAdapter {

    private final ResolveExecutionSettings resolveExecutionSettings;

    private final Logger logger;

    public GradleScriptRunnerAdapter(final ResolveExecutionSettings resolveExecutionSettings, final Logger logger) {
        this.resolveExecutionSettings = resolveExecutionSettings;
        this.logger = logger;
    }

    /**
     * Executes the given script with the executor available in the project. The executor automatically throws an
     * exception if the script execution fails, or does not terminate with a non-zero exit value.
     *
     * @param scriptProperties Script properties.
     * @throws ExecutableNotFoundException When a executable required is not found.
     */
    public void execute(@Nonnull final ScriptProperties scriptProperties) throws ExecutableNotFoundException {
        final ExecutionSettings executionSettings = resolveExecutionSettings.execute(
            scriptProperties.getPackageJsonDirectoryPath(), scriptProperties.getExecutableType(),
            scriptProperties.getNodeInstallDirectory(),
            scriptProperties.getPlatform(), scriptProperties.getScript());
        logger.debug("Execution settings: {}", executionSettings);

        scriptProperties
            .getExecOperations()
            .exec(new ExecSpecAction(executionSettings, this::logExecSpecBeforeExecution))
            .rethrowFailure()
            .assertNormalExitValue();
    }

    private void logExecSpecBeforeExecution(@Nonnull final ExecSpec execSpec) {
        logger.debug("Execution environment: {}", execSpec.getEnvironment());
        logger.info("Running '{}' with arguments: [{}]", execSpec.getExecutable(),
            String.join("], [", execSpec.getArgs()));
    }
}
