package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DirectoryNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.ArchiveEntry;
import org.siouan.frontendgradleplugin.domain.model.Archiver;
import org.siouan.frontendgradleplugin.domain.model.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;

/**
 * Base class of an archiver. When exploding archives, the exploder tries to restore symbolic links and Unix permissions
 * of each entry in the archive. However, on Windows O/S:
 * <ul>
 * <li>Unix permissions are ignored.</li>
 * <li>Exploding will probably fail if the archive contains symbolic links, and the JVM does not run with administrator
 * priviledges.</li>
 * </ul>
 *
 * @param <C> Class of the context used internally to provide access to low-level API.
 * @param <E> Class of an archive entry.
 * @since 1.1.3
 */
public abstract class AbstractArchiver<C extends ArchiverContext, E extends ArchiveEntry> implements Archiver {

    /**
     * Map of binary masks used on Unix modes to extract a single permission.
     */
    private static final Map<Integer, PosixFilePermission> UNIX_MASK_TO_PERMISSION = new HashMap<>();

    static {
        UNIX_MASK_TO_PERMISSION.put(0x100, PosixFilePermission.OWNER_READ); // 0400
        UNIX_MASK_TO_PERMISSION.put(0x080, PosixFilePermission.OWNER_WRITE); // 0200
        UNIX_MASK_TO_PERMISSION.put(0x040, PosixFilePermission.OWNER_EXECUTE); // 0100
        UNIX_MASK_TO_PERMISSION.put(0x020, PosixFilePermission.GROUP_READ); // 0040
        UNIX_MASK_TO_PERMISSION.put(0x010, PosixFilePermission.GROUP_WRITE); // 0020
        UNIX_MASK_TO_PERMISSION.put(0x008, PosixFilePermission.GROUP_EXECUTE); // 0010
        UNIX_MASK_TO_PERMISSION.put(0x004, PosixFilePermission.OTHERS_READ); // 0004
        UNIX_MASK_TO_PERMISSION.put(0x002, PosixFilePermission.OTHERS_WRITE); // 0020
        UNIX_MASK_TO_PERMISSION.put(0x001, PosixFilePermission.OTHERS_EXECUTE); // 0010
    }

    /**
     * Initializes a context to explode an archive. If initialization fails, the archiver is responsible to leave this
     * method with no resources opened, because the context {@link ArchiverContext#close()} method won't be called.
     *
     * @param settings Explode settings.
     * @return Context.
     * @throws ArchiverException If the context cannot be initialized.
     */
    protected abstract C initializeContext(ExplodeSettings settings) throws ArchiverException;

    /**
     * Closes a context.
     *
     * @param context Context.
     * @throws ArchiverException If the context cannot be closed.
     */
    private void closeContext(final C context) throws ArchiverException {
        context.close();
    }

    /**
     * Gets the next entry available in the archive.
     *
     * @param context Context.
     * @return Archive entry.
     * @throws ArchiverException If an error occured when resolving the next entry available.
     */
    protected abstract Optional<E> getNextEntry(C context) throws ArchiverException;

    /**
     * Gets the target path of a symbolic link entry.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @return Target path of the symbolic link described by the entry.
     * @throws ArchiverException If the context cannot be closed.
     */
    protected abstract String getSymbolicLinkTarget(C context, E entry) throws ArchiverException;

    @Override
    public void explode(final ExplodeSettings settings)
        throws SlipAttackException, UnsupportedEntryException, ArchiverException {
        final Path targetDirectory = settings.getTargetDirectory();
        if (!Files.isDirectory(targetDirectory)) {
            throw new DirectoryNotFoundException(targetDirectory);
        }

        C context = null;
        try {
            context = initializeContext(settings);
            Optional<E> entry = getNextEntry(context);
            while (entry.isPresent()) {
                extractEntry(context, entry.get());
                entry = getNextEntry(context);
            }
        } finally {
            if (context != null) {
                closeContext(context);
            }
        }
    }

    /**
     * Extracts the given entry into the destination directory.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @throws SlipAttackException If a slip attack is detected.
     * @throws UnsupportedEntryException If the archive contains an unsupported entry (not a symbolic link, a regular
     * file or a directory).
     * @throws ArchiverException If extraction fails.
     */
    private void extractEntry(final C context, final E entry)
        throws SlipAttackException, UnsupportedEntryException, ArchiverException {
        final Path targetFile = context.getSettings().getTargetDirectory().resolve(entry.getName());
        if (!targetFile
            .normalize()
            .startsWith(context.getSettings().getTargetDirectory().normalize().toString() + File.separatorChar)) {
            throw new SlipAttackException(entry.getName());
        }

        try {
            if (!Files.isDirectory(targetFile.getParent())) {
                Files.createDirectories(targetFile.getParent());
            }
            if (entry.isSymbolicLink()) {
                writeSymbolicLink(context, entry, targetFile);
            } else if (entry.isDirectory()) {
                writeDirectory(targetFile);
            } else if (entry.isFile()) {
                writeRegularFile(context, entry, targetFile);
            } else {
                throw new UnsupportedEntryException(entry.getName());
            }

            if (!entry.isSymbolicLink() && !context.getSettings().getPlatform().isWindowsOs()) {
                Files.setPosixFilePermissions(targetFile, toPosixPermissions(entry.getUnixMode()));
            }
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }
    }

    /**
     * Writes the symbolic link in the file system related to an archive entry.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @param linkFile The symbolic link file.
     * @throws ArchiverException If the target of the link cannot be extracted from the entry.
     * @throws IOException If the link cannot be created.
     */
    private void writeSymbolicLink(final C context, final E entry, final Path linkFile)
        throws ArchiverException, IOException {
        final Path targetPath = linkFile
            .getParent()
            .relativize(linkFile.getParent().resolve(getSymbolicLinkTarget(context, entry)));
        Files.createSymbolicLink(linkFile, targetPath);
    }

    /**
     * Writes a directory at the given path in the file system.
     *
     * @param targetPath Target path.
     * @throws IOException If an I/O error occurs.
     */
    private void writeDirectory(final Path targetPath) throws IOException {
        Files.createDirectory(targetPath);
    }

    /**
     * Writes a file with the content of the given entry in the file system.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @param targetFile Target file.
     * @throws IOException If an I/O error occurs.
     * @throws ArchiverException If the archiver cannot write the file in the file system.
     */
    protected abstract void writeRegularFile(final C context, final E entry, final Path targetFile)
        throws IOException, ArchiverException;

    /**
     * Maps the given Unix mode (e.g. O755) to a set of POSIX permissions.
     *
     * @param unixMode Unix mode.
     * @return Set of permissions.
     */
    private Set<PosixFilePermission> toPosixPermissions(final int unixMode) {
        return UNIX_MASK_TO_PERMISSION
            .entrySet()
            .stream()
            .filter(entry -> (entry.getKey() & unixMode) != 0)
            .map(Map.Entry::getValue)
            .collect(Collectors.toSet());
    }
}
