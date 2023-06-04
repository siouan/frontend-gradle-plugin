package org.siouan.frontendgradleplugin.infrastructure.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

import org.siouan.frontendgradleplugin.domain.ChannelProvider;

/**
 * A provider of channels that delegate calls to the JDK NIO {@link Files} and {@link Channels} classes.
 *
 * @since 2.0.0
 */
public class ChannelProviderImpl implements ChannelProvider {

    @Override
    public ReadableByteChannel getReadableByteChannel(final InputStream inputStream) {
        return Channels.newChannel(inputStream);
    }

    @Override
    public SeekableByteChannel getSeekableByteChannel(final Path filePath) throws IOException {
        return Files.newByteChannel(filePath);
    }

    @Override
    public FileChannel getWritableFileChannelForNewFile(final Path filePath, final OpenOption... options)
        throws IOException {
        return FileChannel.open(filePath, options);
    }
}
