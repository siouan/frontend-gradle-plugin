package org.siouan.frontendgradleplugin.domain;

/**
 * Exception thrown when a package manager specification does not match format {@code <name>@<version>}.
 *
 * @since 7.0.0
 */
public class MalformedPackageManagerSpecification extends FrontendException {

    public MalformedPackageManagerSpecification(final String packageManagerSpecification) {
        super("Malformed package manager specification: '" + packageManagerSpecification + '\'');
    }
}
