package org.siouan.frontendgradleplugin.domain.installer.archiver;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.FileManager;

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
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractArchiver<C extends ArchiverContext, E extends ArchiveEntry>
    implements Archiver, Serializable {

    private static final long serialVersionUID = -3028639659907720045L;

    /**
     * Map of binary masks used on Unix modes to extract a single permission.
     */
    // @formatter:off
    private static final Map<Integer, PosixFilePermission> UNIX_MASK_TO_PERMISSION = Map.of(
        // 0400
        0x100, PosixFilePermission.OWNER_READ,
        // 0200
        0x080, PosixFilePermission.OWNER_WRITE,
        // 0100
        0x040, PosixFilePermission.OWNER_EXECUTE,
        // 0040
        0x020, PosixFilePermission.GROUP_READ,
        // 0020
        0x010, PosixFilePermission.GROUP_WRITE,
        // 0010
        0x008, PosixFilePermission.GROUP_EXECUTE,
        // 0004
        0x004, PosixFilePermission.OTHERS_READ,
        // 0020
        0x002, PosixFilePermission.OTHERS_WRITE,
        // 0010
        0x001, PosixFilePermission.OTHERS_EXECUTE);
    // @formatter:on

    protected final FileManager fileManager;

    /**
     * Initializes a context to explode an archive. If initialization fails, the archiver is responsible to close any
     * resources that may have been created, because the context {@link ArchiverContext#close()} method won't be
     * called.
     *
     * @param explodeCommand Parameters to explode archive content.
     * @return Context.
     * @throws ArchiverException If the context cannot be initialized.
     * @throws IOException If an I/O error occurs.
     */
    protected abstract C initializeContext(ExplodeCommand explodeCommand) throws ArchiverException, IOException;

    /**
     * Gets the next entry available in the archive.
     *
     * @param context Context.
     * @return Archive entry.
     * @throws IOException If an I/O error occurs.
     */
    protected abstract Optional<E> getNextEntry(C context) throws IOException;

    /**
     * Gets the target path of a symbolic link entry.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @return Target path of the symbolic link described by the entry.
     * @throws IOException If an I/O error occurs.
     */
    protected abstract String getSymbolicLinkTarget(C context, E entry) throws IOException;

    @Override
    public void explode(final ExplodeCommand command) throws ArchiverException, IOException {
        final Path targetDirectoryPath = command.getTargetDirectoryPath();
        if (!fileManager.isDirectory(targetDirectoryPath)) {
            throw new DirectoryNotFoundException(targetDirectoryPath);
        }

        try (final C context = initializeContext(command)) {
            Optional<E> entry = getNextEntry(context);
            while (entry.isPresent()) {
                extractEntry(context, entry.get());
                entry = getNextEntry(context);
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
    private void extractEntry(final C context, final E entry) throws ArchiverException, IOException {
        final Path targetFilePath = context.getExplodeCommand().getTargetDirectoryPath().resolve(entry.getName());
        if (!targetFilePath.normalize().startsWith(context.getExplodeCommand().getTargetDirectoryPath().normalize())) {
            throw new SlipAttackException(entry.getName());
        }

        if (!fileManager.isDirectory(targetFilePath.getParent())) {
            fileManager.createDirectories(targetFilePath.getParent());
        }
        if (entry.isSymbolicLink()) {
            writeSymbolicLink(context, entry, targetFilePath);
        } else if (entry.isDirectory()) {
            writeDirectory(targetFilePath);
        } else if (entry.isFile()) {
            writeRegularFile(context, entry, targetFilePath);
        } else {
            throw new UnsupportedEntryException(entry.getName());
        }

        if (!entry.isSymbolicLink() && !context.getExplodeCommand().getPlatform().isWindowsOs()) {
            fileManager.setPosixFilePermissions(targetFilePath, toPosixPermissions(entry.getUnixMode()));
        }
    }

    /**
     * Writes the symbolic link in the file system related to an archive entry.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @param linkFilePath The symbolic link file.
     * @throws ArchiverException If the target of the link cannot be extracted from the entry.
     * @throws IOException If the link cannot be created.
     */
    private void writeSymbolicLink(final C context, final E entry, final Path linkFilePath)
        throws IOException, ArchiverException {
        final Path targetFilePath = linkFilePath.getParent().resolve(getSymbolicLinkTarget(context, entry));
        // Normalization here is very important, because relativize method does not provide the same result on different
        // O/S, when the target path is not normalized (i.e. contains '.' or '..' elements).
        final Path normalizedAndRelativizedTargetFilePath = linkFilePath
            .getParent()
            .relativize(targetFilePath.normalize());
        fileManager.createSymbolicLink(linkFilePath, normalizedAndRelativizedTargetFilePath);
        final Path relativizedTargetFilePath = linkFilePath.getParent().relativize(targetFilePath);
        if (!fileManager.isSameFile(relativizedTargetFilePath, normalizedAndRelativizedTargetFilePath) && (
            fileManager.exists(relativizedTargetFilePath) || fileManager.exists(
                normalizedAndRelativizedTargetFilePath))) {
            throw new InvalidRelativizedSymbolicLinkTargetException(entry.getName(), targetFilePath);
        }
    }

    /**
     * Writes a directory at the given path in the file system.
     *
     * @param directoryPath Target path.
     * @throws IOException If an I/O error occurs.
     */
    private void writeDirectory(final Path directoryPath) throws IOException {
        fileManager.createDirectory(directoryPath);
    }

    /**
     * Writes a file with the content of the given entry in the file system.
     *
     * @param context Context.
     * @param entry Archive entry.
     * @param filePath Target file.
     * @throws IOException If an I/O error occurs.
     */
    protected abstract void writeRegularFile(final C context, final E entry, final Path filePath) throws IOException;

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
            .collect(toSet());
    }
}
