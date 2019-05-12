package org.siouan.frontendgradleplugin.core.archivers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Factory that builds/provides archivers using an internal map based on filename extensions.
 *
 * @since 1.1.3
 */
public class ArchiverFactoryImpl implements ArchiverFactory {

    /**
     * Map of archivers supporting a given extension.
     */
    private static final Map<String, Archiver> REGISTERED_ARCHIVERS = new HashMap<>();

    static {
        REGISTERED_ARCHIVERS.put(".tar.gz", new TarArchiver());
        REGISTERED_ARCHIVERS.put(".zip", new ZipArchiver());
    }

    @Override
    public Optional<Archiver> get(final String extension) {
        return Optional.ofNullable(REGISTERED_ARCHIVERS.get(extension));
    }
}
