package org.siouan.frontendgradleplugin.domain.provider;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Provider of Java NIO {@link Channel}.
 *
 * @since 2.0.0
 */
public interface ChannelProvider {

    /**
     * Downloads the resource at the given URL.
     *
     * @param resourceUrl URL to download the resource.
     * @param proxy Proxy to use for the connection.
     * @return Channel to read the resource content.
     * @throws IOException In case an I/O error occurs.
     */
    @Nonnull
    ReadableByteChannel getReadableByteChannel(@Nonnull URL resourceUrl, @Nonnull final Proxy proxy) throws IOException;

    /**
     * Opens the file at the given path for reading.
     *
     * @param filePath File path.
     * @return Channel to read the file content.
     * @throws IOException In case an I/O error occurs.
     */
    @Nonnull
    SeekableByteChannel getSeekableByteChannel(@Nonnull Path filePath) throws IOException;

    /**
     * Opens the file at the given path for writing.
     *
     * @param filePath File path.
     * @return Channel to write the file content.
     * @throws IOException In case an I/O error occurs.
     */
    @Nonnull
    FileChannel getWritableFileChannelForNewFile(@Nonnull Path filePath) throws IOException;
}
