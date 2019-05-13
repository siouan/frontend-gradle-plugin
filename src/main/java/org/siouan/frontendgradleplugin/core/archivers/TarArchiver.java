package org.siouan.frontendgradleplugin.core.archivers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;
import org.siouan.frontendgradleplugin.core.Utils;

/**
 * A non thread-safe archiver that deals with TAR archives. When exploding archives, the exploder tries to restore
 * symbolic links and Unix permissions of each entry in the archive. However, on Windows O/S:
 * <ul>
 * <li>Unix permissions are ignored.</li>
 * <li>Exploding will probably fail if the archive contains symbolic links, and the JVM does not run with administrator
 * priviledges.</li>
 * </ul>
 *
 * @since 1.1.3
 */
class TarArchiver extends AbstractArchiver<TarArchiverContext, TarEntry> {

    private final byte[] buffer;

    /**
     * Builds an exploder with an internal buffer of 1 KB.
     */
    TarArchiver() {
        this.buffer = new byte[1024];
    }

    @Override
    TarArchiverContext initializeContext(final ExplodeSettings settings) throws ArchiverException {
        final InputStream archiveInputStream;
        try {
            archiveInputStream = Files.newInputStream(settings.getArchiveFile());
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }

        final InputStream uncompressedInputStream;
        try {
            uncompressedInputStream = uncompressInputStream(settings, archiveInputStream);
        } catch (final IOException e) {
            IOUtils.closeQuietly(archiveInputStream);
            throw new ArchiverException(e);
        }

        return new TarArchiverContext(settings, buildLowLevelInputStream(uncompressedInputStream));
    }

    /**
     * Uncompresses the given input stream, if needed.
     *
     * @param settings Explode settings.
     * @param compressedInputStream An input stream that may be compressed.
     * @return Uncompressed input stream, may be the same stream if it is not compressed.
     * @throws IOException If an I/O error occurs.
     */
    InputStream uncompressInputStream(final ExplodeSettings settings, final InputStream compressedInputStream)
        throws IOException {
        final InputStream uncompressedInputStream;
        if (Utils.getExtension(settings.getArchiveFile().getFileName().toString()).filter(Utils::isGzipExtension)
            .isPresent()) {
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
    Optional<TarEntry> getNextEntry(final TarArchiverContext context) throws ArchiverException {
        try {
            return Optional.ofNullable(context.getInputStream().getNextTarEntry()).map(TarEntry::new);
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }
    }

    @Override
    String getSymbolicLinkTarget(final TarArchiverContext context, final TarEntry entry) {
        return entry.getLowLevelEntry().getLinkName();
    }

    @Override
    void writeRegularFile(final TarArchiverContext context, final TarEntry entry, final Path targetFile)
        throws IOException, ArchiverException {
        int bytesRead;
        int bytesToRead = (int) entry.getLowLevelEntry().getSize();
        try (final OutputStream outputStream = Files.newOutputStream(targetFile)) {
            while (bytesToRead > 0) {
                bytesRead = context.getInputStream().read(buffer, 0, Math.min(bytesToRead, buffer.length));
                if (bytesRead == -1) {
                    throw new ArchiverException(
                        "Unexpected EOF when reading entry '" + entry.getName() + "': " + bytesRead + " bytes read / "
                            + bytesToRead + " remaining");
                } else {
                    outputStream.write(buffer, 0, bytesRead);
                    bytesToRead -= bytesRead;
                }
            }
        }
    }
}
