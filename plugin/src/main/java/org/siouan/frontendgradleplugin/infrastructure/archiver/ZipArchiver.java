package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serial;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.installer.archiver.AbstractArchiver;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

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

    @Serial
    private static final long serialVersionUID = -6879163434284590850L;

    public ZipArchiver(final FileManager fileManager) {
        super(fileManager);
    }

    @Override
    protected ZipArchiverContext initializeContext(final ExplodeCommand explodeCommand) throws IOException {
        return new ZipArchiverContext(explodeCommand,
            ZipFile.builder().setFile(explodeCommand.getArchiveFilePath().toFile()).get());
    }

    @Override
    protected Optional<ZipEntry> getNextEntry(final ZipArchiverContext context) {
        return Optional
            .ofNullable(context.getEntries().hasMoreElements() ? context.getEntries().nextElement() : null)
            .map(ZipEntry::new);
    }

    @Override
    protected String getSymbolicLinkTarget(final ZipArchiverContext context, final ZipEntry entry) throws IOException {
        return readSymbolicLinkTarget(context.getZipFile(), entry.getLowLevelEntry());
    }

    @Override
    protected void writeRegularFile(final ZipArchiverContext context, final ZipEntry entry, final Path filePath)
        throws IOException {
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
