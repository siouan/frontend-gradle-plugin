package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.PathUtils;
import org.siouan.frontendgradleplugin.domain.installer.archiver.Archiver;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverProvider;

/**
 * An archive provider.
 *
 * @since 1.1.3
 */
public class ArchiverProviderImpl implements ArchiverProvider, Serializable {

    private static final long serialVersionUID = 5967837524064117384L;

    /**
     * Map of archivers supporting a given extension.
     */
    private final Map<String, Archiver> registeredArchivers;

    public ArchiverProviderImpl(final TarArchiver tarArchiver, final ZipArchiver zipArchiver) {
        this.registeredArchivers = Map.of(".tar.gz", tarArchiver, ".zip", zipArchiver);
    }

    @Override
    public Optional<Archiver> findByArchiveFilePath(final Path archiveFilePath) {
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
