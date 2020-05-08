package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Applies an authorization to a URL connection. This implementation supports only the BASIC scheme.
 *
 * @since 3.0.0
 */
public class ApplyAuthorization {

    public static final String AUTHORIZATION_TYPE = "Basic";

    public static final String SECURITY_WARNING =
        "SECURITY WARNING: using basic authentication scheme with non-secure protocols exposes credentials to the world."
            + " Consider using HTTPS protocol if possible for URL: '{}'";

    private static final String HTTPS_PROTOCOL = "https";

    private final Logger logger;

    public ApplyAuthorization(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Applies an authentication token on the given connection, with a given key. For instance, the key allows to set a
     * token either for the remote server, or an intermediate proxy server.
     *
     * @param urlConnection Connection to a URL.
     * @param authorizationKeyName Name of the authorization key to set in the connection.
     * @param credentials Credentials.
     */
    public void execute(@Nonnull final URLConnection urlConnection, @Nonnull final String authorizationKeyName,
        @Nonnull final Credentials credentials) {
        final URL url = urlConnection.getURL();
        if (!url.getProtocol().equalsIgnoreCase(HTTPS_PROTOCOL)) {
            logger.warn(SECURITY_WARNING, url);
        }
        final String basicCredentials = credentials.getUsername() + ':' + credentials.getPassword();
        urlConnection.setRequestProperty(authorizationKeyName,
            AUTHORIZATION_TYPE + ' ' + Base64.getEncoder().encodeToString(basicCredentials.getBytes()));
    }
}
