package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;

/**
 * Gets the path to an executable.
 *
 * @since 5.0.1
 */
public class GetExecutablePath {

    private final GetCorepackExecutablePath getCorepackExecutablePath;

    private final GetNodeExecutablePath getNodeExecutablePath;

    private final GetNpmExecutablePath getNpmExecutablePath;

    private final GetPnpmExecutablePath getPnpmExecutablePath;

    private final GetYarnExecutablePath getYarnExecutablePath;

    public GetExecutablePath(@Nonnull final GetCorepackExecutablePath getCorepackExecutablePath,
        @Nonnull final GetNodeExecutablePath getNodeExecutablePath,
        @Nonnull final GetNpmExecutablePath getNpmExecutablePath,
        @Nonnull final GetPnpmExecutablePath getPnpmExecutablePath,
        @Nonnull final GetYarnExecutablePath getYarnExecutablePath) {
        this.getCorepackExecutablePath = getCorepackExecutablePath;
        this.getNodeExecutablePath = getNodeExecutablePath;
        this.getNpmExecutablePath = getNpmExecutablePath;
        this.getPnpmExecutablePath = getPnpmExecutablePath;
        this.getYarnExecutablePath = getYarnExecutablePath;
    }

    /**
     * Resolves the path of the executable.
     *
     * @param getExecutablePathQuery Query parameters.
     * @return Executable path.
     * @see ExecutableType
     */
    public Path execute(@Nonnull final GetExecutablePathQuery getExecutablePathQuery) {
        final ExecutableType executableType = getExecutablePathQuery.getExecutableType();
        switch (executableType) {
        case COREPACK:
            return getCorepackExecutablePath.execute(getExecutablePathQuery.getNodeInstallDirectoryPath(),
                getExecutablePathQuery.getPlatform());
        case NODE:
            return getNodeExecutablePath.execute(getExecutablePathQuery.getNodeInstallDirectoryPath(),
                getExecutablePathQuery.getPlatform());
        case NPM:
            return getNpmExecutablePath.execute(getExecutablePathQuery.getNodeInstallDirectoryPath(),
                getExecutablePathQuery.getPlatform());
        case PNPM:
            return getPnpmExecutablePath.execute(getExecutablePathQuery.getNodeInstallDirectoryPath(),
                getExecutablePathQuery.getPlatform());
        case YARN:
            return getYarnExecutablePath.execute(getExecutablePathQuery.getNodeInstallDirectoryPath(),
                getExecutablePathQuery.getPlatform());
        default:
            throw new IllegalArgumentException("Unsupported type of executable: " + executableType);
        }
    }
}
