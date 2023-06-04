package org.siouan.frontendgradleplugin.domain;

/**
 * Exception thrown when the underlying platform is not supported by the plugin.
 *
 * @since 2.0.0
 */
public class UnsupportedPlatformException extends FrontendException {

    public UnsupportedPlatformException(final Platform platform) {
        super("This platform is not supported yet: " + platform);
    }
}
