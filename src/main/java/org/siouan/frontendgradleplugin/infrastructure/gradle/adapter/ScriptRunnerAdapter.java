package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script.
 */
public class ScriptRunnerAdapter {

    private final Logger logger;

    /**
     * Builds an adapter run a script.
     *
     * @param logger Logger.
     */
    public ScriptRunnerAdapter(final Logger logger) {
        this.logger = logger;
    }

    public void run(@Nonnull final ScriptProperties scriptProperties) throws ExecutableNotFoundException {
        scriptProperties
            .getProject()
            .exec(new ExecSpecAction(scriptProperties.getPackageJsonDirectory(), scriptProperties.getExecutableType(),
                scriptProperties.getNodeInstallDirectory(), scriptProperties.getYarnInstallDirectory(),
                scriptProperties.getPlatform(), scriptProperties.getScript(), execSpec -> {
                logger.debug(execSpec.getEnvironment().toString());
                logger.log(
                    "Running '" + execSpec.getExecutable() + " with args: [" + String.join("], [", execSpec.getArgs())
                        + ']');
            }))
            .rethrowFailure()
            .assertNormalExitValue();
    }
}
