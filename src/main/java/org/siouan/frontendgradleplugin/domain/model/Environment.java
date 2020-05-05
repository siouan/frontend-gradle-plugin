package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nullable;

/**
 * This class represents the environment of the current process, and gives access to some environment variables.
 *
 * @since 3.0.0
 */
public class Environment {

    /**
     * Path to a directory containing a global Node.js distribution.
     */
    private final Path nodeInstallDirectoryPath;

    /**
     * Path to a directory containing a global Yarn distribution.
     */
    private final Path yarnInstallDirectoryPath;

    /**
     * Builds an environment.
     *
     * @param nodeInstallDirectoryPath Path to a directory containing a global Node.js distribution.
     * @param yarnInstallDirectoryPath Path to a directory containing a global Yarn distribution.
     */
    public Environment(@Nullable final Path nodeInstallDirectoryPath, @Nullable final Path yarnInstallDirectoryPath) {
        this.nodeInstallDirectoryPath = nodeInstallDirectoryPath;
        this.yarnInstallDirectoryPath = yarnInstallDirectoryPath;
    }

    /**
     * Gets the path to a directory containing a global Node.js distribution.
     *
     * @return Path.
     */
    @Nullable
    public Path getNodeInstallDirectoryPath() {
        return nodeInstallDirectoryPath;
    }

    /**
     * Gets the path to a directory containing a global Yarn distribution.
     *
     * @return Path.
     */
    @Nullable
    public Path getYarnInstallDirectoryPath() {
        return yarnInstallDirectoryPath;
    }

    @Override
    public String toString() {
        return Environment.class.getSimpleName() + " {nodeInstallDirectory=" + nodeInstallDirectoryPath
            + ", yarnInstallDirectory=" + yarnInstallDirectoryPath + '}';
    }
}
