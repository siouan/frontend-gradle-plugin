package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Builder;
import org.siouan.frontendgradleplugin.domain.installer.HttpResponse;

/**
 * @since 4.0.1
 */
@Builder
public class LocalFileHttpResponse implements HttpResponse {

    public static final String PROTOCOL = "file";

    private final Path localFilePath;

    @Override
    public String getProtocol() {
        return PROTOCOL;
    }

    @Override
    public String getVersion() {
        return "N/A";
    }

    @Override
    public int getStatusCode() {
        return Files.exists(localFilePath) ? 200 : 404;
    }

    @Override
    public String getReasonPhrase() {
        return Files.exists(localFilePath) ? "Ok" : "Not Found";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(localFilePath);
    }

    @Override
    public void close() {
        // Nothing to close here
    }
}
