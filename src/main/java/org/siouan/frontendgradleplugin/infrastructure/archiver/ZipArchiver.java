package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.usecase.AbstractArchiver;

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
public class ZipArchiver extends AbstractArchiver<ZipArchiverContext, ZipEntry> {

    public ZipArchiver(final FileManager fileManager) {
        super(fileManager);
    }

    @Nonnull
    @Override
    protected ZipArchiverContext initializeContext(@Nonnull final ExplodeSettings settings) throws IOException {
        return new ZipArchiverContext(settings, new ZipFile(settings.getArchiveFilePath().toFile()));
    }

    @Nonnull
    @Override
    protected Optional<ZipEntry> getNextEntry(@Nonnull final ZipArchiverContext context) {
        return Optional
            .ofNullable(context.getEntries().hasMoreElements() ? context.getEntries().nextElement() : null)
            .map(ZipEntry::new);
    }

    @Nonnull
    @Override
    protected String getSymbolicLinkTarget(@Nonnull final ZipArchiverContext context, @Nonnull final ZipEntry entry)
        throws IOException {
        return readSymbolicLinkTarget(context.getZipFile(), entry.getLowLevelEntry());
    }

    @Override
    protected void writeRegularFile(@Nonnull final ZipArchiverContext context, @Nonnull final ZipEntry entry,
        @Nonnull final Path filePath) throws IOException {
        try (final InputStream entryInputStream = context.getZipFile().getInputStream(entry.getLowLevelEntry())) {
            fileManager.copy(entryInputStream, filePath);
        }
    }

    String readSymbolicLinkTarget(final ZipFile zipFile, final ZipArchiveEntry entry) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
            return reader.readLine();
        }
    }
}
