package org.siouan.frontendgradleplugin.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * Provider of Java NIO {@link Channel}.
 *
 * @since 2.0.0
 */
public interface ChannelProvider {

    /**
     * Opens a channel to read the content from an input stream. Closing the channel closes the input stream.
     *
     * @param inputStream Input stream.
     * @return Channel to read the resource content.
     */
    ReadableByteChannel getReadableByteChannel(InputStream inputStream);

    /**
     * Opens the file at the given path for reading.
     *
     * @param filePath File path.
     * @return Channel to read the file content.
     * @throws IOException In case an I/O error occurs.
     */
    SeekableByteChannel getSeekableByteChannel(Path filePath) throws IOException;

    /**
     * Opens the file at the given path for writing.
     *
     * @param filePath File path.
     * @param options Open options.
     * @return Channel to write the file content.
     * @throws IOException In case an I/O error occurs.
     */
    FileChannel getWritableFileChannelForNewFile(Path filePath, OpenOption... options) throws IOException;
}
