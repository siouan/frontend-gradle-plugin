package org.siouan.frontendgradleplugin.domain;

/**
 * Exception thrown when a package manager specification ({@code <name>@<version>}) refers to an unsupported package
 * manager.
 *
 * @since 7.0.0
 */
public class UnsupportedPackageManagerException extends FrontendException {

    public UnsupportedPackageManagerException(final String packageManagerName) {
        super("Unsupported package manager: '" + packageManagerName + '\'');
    }
}
