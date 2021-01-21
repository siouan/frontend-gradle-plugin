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
     * Builds an environment.
     *
     * @param nodeInstallDirectoryPath Path to a directory containing a global Node.js distribution.
     */
    public Environment(@Nullable final Path nodeInstallDirectoryPath) {
        this.nodeInstallDirectoryPath = nodeInstallDirectoryPath;
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

    @Override
    public String toString() {
        return Environment.class.getSimpleName() + " {nodeInstallDirectory=" + nodeInstallDirectoryPath + '}';
    }
}
