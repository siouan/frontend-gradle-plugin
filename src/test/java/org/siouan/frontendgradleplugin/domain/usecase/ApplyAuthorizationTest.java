package org.siouan.frontendgradleplugin.domain.usecase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * <b>Note on verifications</b>: exhaustive verification of interactions on the resource output channel is not possible
 * with this version of Mockito: it throws a {@link NoInteractionsWanted} exception because the {@link
 * FileChannel#close()} method call has not been verified. However, this method being declared final, it can not be
 * verified by definition.
 */
@ExtendWith(MockitoExtension.class)
class ApplyAuthorizationTest {

    private static final String AUTHORIZATION_KEY_NAME = "Key";

    private static final String USERNAME = "usKgrolg4FE6a9";

    private static final String PASSWORD = "]n#O|yOX[n6X@1.R";

    @Mock
    private Logger logger;

    @Mock
    private URLConnection urlConnection;

    @InjectMocks
    private ApplyAuthorization usecase;

    @Test
    void shouldApplyAuthorization() throws MalformedURLException {
        final Credentials credentials = new Credentials(USERNAME, PASSWORD);
        when(urlConnection.getURL()).thenReturn(new URL("https://foo.bar"));

        usecase.execute(urlConnection, AUTHORIZATION_KEY_NAME, credentials);

        verify(urlConnection).setRequestProperty(AUTHORIZATION_KEY_NAME,
            "Basic dXNLZ3JvbGc0RkU2YTk6XW4jT3x5T1hbbjZYQDEuUg==");
        verifyNoMoreInteractions(logger, urlConnection);
    }

    @Test
    void shouldApplyAuthorizationWithSecurityWarning() throws MalformedURLException {
        final URL url = new URL("http://foo.bar");
        final Credentials credentials = new Credentials(USERNAME, PASSWORD);
        when(urlConnection.getURL()).thenReturn(url);

        usecase.execute(urlConnection, AUTHORIZATION_KEY_NAME, credentials);

        verify(logger).warn(ApplyAuthorization.SECURITY_WARNING, url);
        verify(urlConnection).setRequestProperty(AUTHORIZATION_KEY_NAME,
            "Basic dXNLZ3JvbGc0RkU2YTk6XW4jT3x5T1hbbjZYQDEuUg==");
        verifyNoMoreInteractions(logger, urlConnection);
    }
}
