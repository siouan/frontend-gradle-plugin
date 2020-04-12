package org.siouan.frontendgradleplugin.domain.exception;

import java.net.URL;
import javax.annotation.Nonnull;

/**
 * Exception thrown when the URL to download a distribution is not valid: the URL must contain a non-empty path part
 * (e.g. can not be {@code https://<domain>/} or {@code https://<domain>/directory/}). It can not end with a trailing slash '/'
 * character.
 *
 * @since 2.0.0
 */
public class InvalidDistributionUrlException extends FrontendException {

    public InvalidDistributionUrlException(@Nonnull final URL distributionUrl) {
        super("Distribution URL must have a path with a non-empty trailing file name: '" + distributionUrl + '\'');
    }
}
