package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.PathUtils;
import org.siouan.frontendgradleplugin.domain.installer.archiver.AbstractArchiver;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

/**
 * A non thread-safe archiver that deals with TAR archives and uses a 1 KB buffer to extract entries. When exploding
 * archives, the exploder tries to restore symbolic links and Unix permissions of each entry in the archive. However, on
 * Windows O/S:
 * <ul>
 * <li>Unix permissions are ignored.</li>
 * <li>Exploding will probably fail if the archive contains symbolic links, and the JVM does not run with administrator
 * priviledges.</li>
 * </ul>
 *
 * @since 1.1.3
 */
public class TarArchiver extends AbstractArchiver<TarArchiverContext, TarEntry> {

    private final byte[] buffer;

    public TarArchiver(final FileManager fileManager) {
        super(fileManager);
        this.buffer = new byte[1024];
    }

    @Override
    protected TarArchiverContext initializeContext(final ExplodeCommand explodeCommand) throws IOException {
        InputStream archiveInputStream = null;
        try {
            archiveInputStream = fileManager.newInputStream(explodeCommand.getArchiveFilePath());
            // In case of creating the uncompress input stream fails, the archive input stream must be closed.
            return new TarArchiverContext(explodeCommand,
                buildLowLevelInputStream(uncompressInputStream(explodeCommand, archiveInputStream)));
        } catch (final IOException e) {
            if (archiveInputStream != null) {
                archiveInputStream.close();
            }
            throw e;
        }
    }

    /**
     * Uncompresses the given input stream, if needed.
     *
     * @param command Parameters to explode archive content.
     * @param compressedInputStream An input stream that may be compressed.
     * @return Uncompressed input stream, may be the same stream if it is not compressed.
     * @throws IOException If an I/O error occurs.
     */
    InputStream uncompressInputStream(final ExplodeCommand command, final InputStream compressedInputStream)
        throws IOException {
        final InputStream uncompressedInputStream;
        if (PathUtils.getExtension(command.getArchiveFilePath()).filter(PathUtils::isGzipExtension).isPresent()) {
            uncompressedInputStream = new GzipCompressorInputStream(compressedInputStream);
        } else {
            uncompressedInputStream = compressedInputStream;
        }
        return uncompressedInputStream;
    }

    /**
     * Builds the low-level input stream that will be used to read entries.
     *
     * @param inputStream High-level uncompressed input stream.
     * @return Input stream.
     */
    TarArchiveInputStream buildLowLevelInputStream(final InputStream inputStream) {
        return new TarArchiveInputStream(inputStream);
    }

    @Override
    protected Optional<TarEntry> getNextEntry(final TarArchiverContext context) throws IOException {
        return Optional.ofNullable(context.getInputStream().getNextTarEntry()).map(TarEntry::new);
    }

    @Override
    protected String getSymbolicLinkTarget(final TarArchiverContext context, final TarEntry entry) {
        return entry.getLowLevelEntry().getLinkName();
    }

    @Override
    protected void writeRegularFile(final TarArchiverContext context, final TarEntry entry, final Path filePath)
        throws IOException {
        final long entrySize = entry.getLowLevelEntry().getSize();
        int bytesRead;
        int bytesToRead = (int) entrySize;
        try (final OutputStream outputStream = fileManager.newOutputStream(filePath)) {
            while (bytesToRead > 0) {
                bytesRead = context.getInputStream().read(buffer, 0, Math.min(bytesToRead, buffer.length));
                if (bytesRead == -1) {
                    throw new UnexpectedEofException(entry.getName(), entrySize, bytesRead);
                } else {
                    outputStream.write(buffer, 0, bytesRead);
                    bytesToRead -= bytesRead;
                }
            }
        }
    }
}
