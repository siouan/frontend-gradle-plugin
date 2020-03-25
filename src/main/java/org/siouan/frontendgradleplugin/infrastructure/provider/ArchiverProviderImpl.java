package org.siouan.frontendgradleplugin.infrastructure.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.model.Archiver;
import org.siouan.frontendgradleplugin.infrastructure.archiver.TarArchiver;
import org.siouan.frontendgradleplugin.infrastructure.archiver.ZipArchiver;

/**
 * Factory that builds/provides archivers using an internal map based on filename extensions.
 *
 * @since 1.1.3
 */
public class ArchiverProviderImpl implements ArchiverProvider {

    /**
     * Map of archivers supporting a given extension.
     */
    private static final Map<String, Archiver> REGISTERED_ARCHIVERS = new HashMap<>();

    static {
        REGISTERED_ARCHIVERS.put(".tar.gz", new TarArchiver());
        REGISTERED_ARCHIVERS.put(".zip", new ZipArchiver());
    }

    @Override
    public Optional<Archiver> findByFilenameExtension(final String extension) {
        return Optional.ofNullable(REGISTERED_ARCHIVERS.get(extension));
    }
}
