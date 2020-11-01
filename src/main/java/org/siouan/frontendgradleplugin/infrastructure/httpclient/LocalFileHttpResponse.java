package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.HttpResponse;

/**
 * @since 4.0.1
 */
public class LocalFileHttpResponse implements HttpResponse {

    public static final String PROTOCOL = "file";

    private final Path localFilePath;

    public LocalFileHttpResponse(@Nonnull final Path localFilePath) {
        this.localFilePath = localFilePath;
    }

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
    @Nullable
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(localFilePath);
    }

    @Override
    public void close() {
        // Nothing to close here
    }
}
