package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;
import org.siouan.frontendgradleplugin.domain.model.PackageManagerType;
import org.siouan.frontendgradleplugin.domain.model.ResolvePackageManagerQuery;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Resolves the package manager applicable in a project.
 *
 * @since 7.0.0
 */
public class ResolvePackageManager {

    private final FileManager fileManager;

    private final GetExecutablePath getExecutablePath;

    private final ParsePackageManagerType parsePackageManagerType;

    public ResolvePackageManager(@Nonnull final ParsePackageManagerType parsePackageManagerType,
        @Nonnull final GetExecutablePath getExecutablePath, @Nonnull final FileManager fileManager) {
        this.parsePackageManagerType = parsePackageManagerType;
        this.getExecutablePath = getExecutablePath;
        this.fileManager = fileManager;
    }

    public void execute(@Nonnull final ResolvePackageManagerQuery query)
        throws UnsupportedPackageManagerException, IOException {
        final PackageManagerType packageManagerType = parsePackageManagerType.execute(query.getMetadataFilePath());
        fileManager.writeString(query.getPackageManagerNameFilePath(), packageManagerType.getPackageManagerName(),
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        fileManager.writeString(query.getPackageManagerExecutablePathFilePath(), getExecutablePath
            .execute(
                new GetExecutablePathQuery(packageManagerType.getExecutableType(), query.getNodeInstallDirectoryPath(),
                    query.getPlatform()))
            .toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
