package org.siouan.frontendgradleplugin.domain.model;

import java.util.Arrays;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enumeration of supported package managers.
 *
 * @since 7.0.0
 */
public enum PackageManagerType {

    NPM("npm", ExecutableType.NPM),
    PNPM("pnpm", ExecutableType.PNPM),
    YARN("yarn", ExecutableType.YARN);

    private final String packageManagerName;

    private final ExecutableType executableType;

    PackageManagerType(@Nonnull final String packageManagerName, @Nonnull final ExecutableType executableType) {
        this.packageManagerName = packageManagerName;
        this.executableType = executableType;
    }

    @Nonnull
    public String getPackageManagerName() {
        return this.packageManagerName;
    }

    @Nonnull
    public ExecutableType getExecutableType() {
        return executableType;
    }

    @Nonnull
    public static Optional<PackageManagerType> fromPackageName(@Nullable final String packageName) {
        return Arrays
            .stream(values())
            .filter(packageManagerType -> packageManagerType.packageManagerName.equals(packageName))
            .findAny();
    }
}
