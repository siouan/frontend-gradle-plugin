package org.siouan.frontendgradleplugin.infrastructure.provider;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Archiver;
import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.util.PathUtils;
import org.siouan.frontendgradleplugin.infrastructure.archiver.TarArchiver;
import org.siouan.frontendgradleplugin.infrastructure.archiver.ZipArchiver;

/**
 * An archive provider.
 *
 * @since 1.1.3
 */
public class ArchiverProviderImpl implements ArchiverProvider {

    /**
     * Map of archivers supporting a given extension.
     */
    private final Map<String, Archiver> registeredArchivers;

    public ArchiverProviderImpl(final TarArchiver tarArchiver, final ZipArchiver zipArchiver) {
        this.registeredArchivers = new HashMap<>();
        this.registeredArchivers.put(".tar.gz", tarArchiver);
        this.registeredArchivers.put(".zip", zipArchiver);
    }

    @Override
    @Nonnull
    public Optional<Archiver> findByArchiveFilePath(@Nonnull final Path archiveFilePath) {
        return PathUtils.getExtension(archiveFilePath).flatMap(extension -> {
            final Optional<String> newExtension;
            if (PathUtils.isGzipExtension(extension)) {
                // Dealing with GZIP archive file names such as '<file>.<real-extension>.gz'
                // Extract the compound extension '.<real-extension>.gz'
                newExtension = PathUtils
                    .getExtension(PathUtils.removeExtension(archiveFilePath))
                    .map(ext -> ext + extension);
            } else {
                newExtension = Optional.of(extension);
            }
            return newExtension;
        }).map(registeredArchivers::get);
    }
}
