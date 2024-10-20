package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;

/**
 * This task installs a specific version of Corepack, which will override the default version embedded in the Node.js
 * distribution (by executing command {@code node -g install corepack[@<corepackVersion>]}).
 *
 * @since 8.1.0
 */
public class InstallCorepackTask extends AbstractRunCommandTask {

    /**
     * NPM command to install globally Corepack.
     */
    private static final String INSTALL_COREPACK_COMMAND = "install -g corepack";

    /**
     * Value for the {@link #corepackVersion} property that will lead to the installation of the latest version of
     * Corepack.
     */
    public static final String LATEST_VERSION_ARGUMENT = "latest";

    /**
     * Path to the Corepack distribution file relative to the Node.js root directory.
     */
    public static final String COREPACK_MODULE_PATH = "node_modules/corepack";

    /**
     * Name of the environment variable that prevents Corepack to fail during update with NPM, if the project uses
     * another package manager.
     */
    public static final String COREPACK_ENABLE_STRICT_VARIABLE = "COREPACK_ENABLE_STRICT";

    /**
     * Version of Corepack that should be installed: may be a specific version number such as {@code X.Y.Z} or the
     * {@link #LATEST_VERSION_ARGUMENT} keyword to install the latest version available.
     */
    private final Property<String> corepackVersion;

    /**
     * Directory containing the Corepack module for Node.js.
     */
    private final DirectoryProperty corepackModuleDirectory;

    @Inject
    public InstallCorepackTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.NPM);
        corepackVersion = objectFactory.property(String.class);
        corepackModuleDirectory = objectFactory.directoryProperty();
        corepackModuleDirectory.set(getProject()
            .getLayout()
            .dir(getNodeInstallDirectory().map(f -> f.toPath().resolve(COREPACK_MODULE_PATH).toFile())));
    }

    @Input
    public Property<String> getCorepackVersion() {
        return corepackVersion;
    }

    @OutputDirectory
    public DirectoryProperty getCorepackModuleDirectory() {
        return corepackModuleDirectory;
    }

    @Override
    public void execute() throws NonRunnableTaskException, BeanRegistryException {
        final StringBuilder scriptBuilder = new StringBuilder(INSTALL_COREPACK_COMMAND);
        final String version = corepackVersion.get();
        if (!version.equals(LATEST_VERSION_ARGUMENT)) {
            scriptBuilder.append("@");
            scriptBuilder.append(version);
        }
        this.executableArgs.set(scriptBuilder.toString());
        this.environmentVariables.put(COREPACK_ENABLE_STRICT_VARIABLE, "0");

        super.execute();
    }
}
