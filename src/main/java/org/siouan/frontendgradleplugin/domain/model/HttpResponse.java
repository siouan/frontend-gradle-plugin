package org.siouan.frontendgradleplugin.domain.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface of a HTTP response.
 *
 * @since 4.0.1
 */
public interface HttpResponse extends Closeable {

    /**
     * Gets the protocol of the HTTP response.
     *
     * @return Protocol.
     */
    String getProtocol();

    /**
     * Gets the protocol version.
     *
     * @return Protocol version.
     */
    String getVersion();

    /**
     * Gets the status code.
     *
     * @return Status code.
     */
    int getStatusCode();

    /**
     * Gets the reason mentioned after the status code in the response.
     *
     * @return Reason.
     */
    String getReasonPhrase();

    /**
     * Gets an input stream to read the body of the HTTP response.
     *
     * @return Input stream.
     * @throws IOException If an I/O error occurs.
     */
    InputStream getInputStream() throws IOException;
}
