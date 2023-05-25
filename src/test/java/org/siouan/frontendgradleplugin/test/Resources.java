package org.siouan.frontendgradleplugin.test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class providing static utilities only to manipulate resources.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Resources {

    /**
     * Gets the path to a resource accessible by this class' class loader.
     *
     * @param resourceName Resource name.
     * @return Path.
     */
    public static Path getResourcePath(final String resourceName) {
        final URL resourceUrl = getResourceUrl(resourceName);
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
    public static URL getResourceUrl(final String resourceName) {
        return Optional
            .ofNullable(Resources.class.getClassLoader().getResource(resourceName))
            .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceName));
    }
}
