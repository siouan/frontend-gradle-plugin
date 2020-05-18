package org.siouan.frontendgradleplugin.test.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Class providing static utilities only to manipulate resources.
 */
public final class Resources {

    private Resources() {
    }

    /**
     * Gets the path to a resource accessible by this class' class loader.
     *
     * @param resourceName Resource name.
     * @return Path.
     */
    @Nonnull
    public static Path getResourcePath(@Nonnull final String resourceName) {
        final URL resourceUrl = getResourceUrl(resourceName);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        try {
            return Paths.get(resourceUrl.toURI());
        } catch (final URISyntaxException e) {
            throw new RuntimeException("Unexpected exception when getting path for resource: " + resourceName, e);
        }
    }

    /**
     * Gets the URL to a resource accessible by this class' class loader.
     *
     * @param resourceName Resource name.
     * @return URL.
     */
    @Nonnull
    public static URL getResourceUrl(@Nonnull final String resourceName) {
        return Optional
            .ofNullable(Resources.class.getClassLoader().getResource(resourceName))
            .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceName));
    }
}
