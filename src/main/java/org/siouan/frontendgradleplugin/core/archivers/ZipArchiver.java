package org.siouan.frontendgradleplugin.core.archivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;

/**
 * An archiver that deals with ZIP archives. When exploding archives, the archiver tries to restore symbolic links and
 * Unix permissions of each entry in the archive. However, on Windows O/S:
 * <ul>
 * <li>Unix permissions are ignored.</li>
 * <li>Exploding will probably fail if the archive contains symbolic links, and the JVM does not run with administrator
 * priviledges.</li>
 * </ul>
 *
 * @since 1.1.3
 */
class ZipArchiver extends AbstractArchiver<ZipArchiverContext, ZipEntry> {

    @Override
    ZipArchiverContext initializeContext(final ExplodeSettings settings) throws ArchiverException {
        try {
            return new ZipArchiverContext(settings, new ZipFile(settings.getArchiveFile().toFile()));
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }
    }

    @Override
    Optional<ZipEntry> getNextEntry(final ZipArchiverContext context) {
        return Optional.ofNullable(context.getEntries().hasMoreElements() ? context.getEntries().nextElement() : null)
            .map(ZipEntry::new);
    }

    @Override
    String getSymbolicLinkTarget(final ZipArchiverContext context, final ZipEntry entry) throws ArchiverException {
        final String target;
        try {
            target = readSymbolicLinkTarget(context.getZipFile(), entry.getLowLevelEntry());
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }
        return target;
    }

    @Override
    void writeRegularFile(final ZipArchiverContext context, final ZipEntry entry, final Path targetFile)
        throws IOException {
        try (final InputStream entryInputStream = context.getZipFile().getInputStream(entry.getLowLevelEntry())) {
            Files.copy(entryInputStream, targetFile);
        }
    }

    String readSymbolicLinkTarget(final ZipFile zipFile, final ZipArchiveEntry entry) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
            return reader.readLine();
        }
    }
}
