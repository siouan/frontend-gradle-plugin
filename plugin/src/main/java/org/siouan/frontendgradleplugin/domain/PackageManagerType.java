package org.siouan.frontendgradleplugin.domain;

import java.util.Arrays;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of supported package managers.
 *
 * @since 7.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum PackageManagerType {

    NPM("npm", ExecutableType.NPM),
    PNPM("pnpm", ExecutableType.PNPM),
    YARN("yarn", ExecutableType.YARN);

    private final String packageManagerName;

    private final ExecutableType executableType;

    public static Optional<PackageManagerType> fromPackageManagerName(final String packageManagerName) {
        return Arrays
            .stream(values())
            .filter(packageManagerType -> packageManagerType.packageManagerName.equals(packageManagerName))
            .findAny();
    }
}
