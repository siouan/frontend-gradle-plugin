package org.siouan.frontendgradleplugin.domain.provider;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public interface ChannelProvider {

    ReadableByteChannel getReadableByteChannel(URL resourceUrl) throws IOException;

    FileChannel getWritableFileChannelForNewFile(Path filePath) throws IOException;
}
