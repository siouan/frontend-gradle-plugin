package org.siouan.frontendgradleplugin.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the {@code packageManager} property in a metadata file {@code package.json}. The specification should match
 * pattern {@code <packageManagerName>@x.y.z[+<hash>]}. This parser does not support specifications
 * containing an URL instead of the version number.
 *
 * @since 7.0.0
 * @see <a href="https://github.com/nodejs/corepack#when-authoring-packages">Specification supported by Corepack</a>
 */
public class ParsePackageManagerSpecification {

    private static final String PACKAGE_MANAGER_NAME_GROUP = "packageManagerName";

    private static final String PACKAGE_MANAGER_VERSION_GROUP = "packageManagerVersion";

    private static final Pattern PACKAGE_MANAGER_PATTERN = Pattern.compile(
        "^(?<" + PACKAGE_MANAGER_NAME_GROUP + ">[\\w\\-]+)@(?<" + PACKAGE_MANAGER_VERSION_GROUP
            + ">\\d+\\.\\d+\\.\\d+)(?:\\+.*)?$");

    public PackageManager execute(final String packageManagerSpecification)
        throws MalformedPackageManagerSpecification, UnsupportedPackageManagerException {
        final Matcher matcher = PACKAGE_MANAGER_PATTERN.matcher(packageManagerSpecification);
        if (!matcher.matches()) {
            throw new MalformedPackageManagerSpecification(packageManagerSpecification);
        }
        final String packageManagerName = matcher.group(PACKAGE_MANAGER_NAME_GROUP);
        final String packageManagerVersion = matcher.group(PACKAGE_MANAGER_VERSION_GROUP);
        return PackageManager
            .builder()
            .type(PackageManagerType
                .fromPackageManagerName(packageManagerName)
                .orElseThrow(() -> new UnsupportedPackageManagerException(packageManagerName)))
            .version(packageManagerVersion)
            .build();
    }
}
