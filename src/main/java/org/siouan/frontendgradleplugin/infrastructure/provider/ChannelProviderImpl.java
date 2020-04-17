package org.siouan.frontendgradleplugin.infrastructure.provider;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;

/**
 * A provider of channels that delegate calls to the JDK NIO {@link Files} and {@link Channels} classes.
 *
 * @since 2.0.0
 */
public class ChannelProviderImpl implements ChannelProvider {

    @Override
    @Nonnull
    public ReadableByteChannel getReadableByteChannel(@Nonnull final URL resourceUrl, @Nonnull final Proxy proxy)
        throws IOException {
        return Channels.newChannel(resourceUrl.openConnection(proxy).getInputStream());
    }

    @Override
    @Nonnull
    public SeekableByteChannel getSeekableByteChannel(@Nonnull final Path filePath) throws IOException {
        return Files.newByteChannel(filePath);
    }

    @Override
    @Nonnull
    public FileChannel getWritableFileChannelForNewFile(@Nonnull final Path filePath) throws IOException {
        return FileChannel.open(filePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
    }
}
