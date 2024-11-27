package org.siouan.frontendgradleplugin.infrastructure.system;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Implementation that delegate calls to the JDK NIO {@link Files} class.
 *
 * @since 2.0.0
 */
public class FileManagerImpl implements FileManager {

    @Serial
    private static final long serialVersionUID = -4492951623732511344L;

    @Override
    public long copy(final InputStream inputStream, final Path filePath) throws IOException {
        return Files.copy(inputStream, filePath);
    }

    @Override
    public void copyFileTree(final Path sourcePath, final Path targetPath) throws IOException {
        Files.walkFileTree(sourcePath, new FileCopyVisitor(sourcePath, targetPath));
    }

    @Override
    public Path createDirectories(final Path path) throws IOException {
        return Files.createDirectories(path);
    }

    @Override
    public Path createDirectory(final Path directoryPath) throws IOException {
        return Files.createDirectory(directoryPath);
    }

    @Override
    public Path createSymbolicLink(final Path linkFilePath, final Path targetFilePath) throws IOException {
        return Files.createSymbolicLink(linkFilePath, targetFilePath);
    }

    @Override
    public void delete(final Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public boolean deleteIfExists(final Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    @Override
    public void deleteFileTree(final Path rootPath, final boolean deleteRootEnabled) throws IOException {
        if (Files.exists(rootPath)) {
            Files.walkFileTree(rootPath, new FileDeleteVisitor(rootPath, deleteRootEnabled));
        }
    }

    @Override
    public boolean exists(final Path filePath) {
        return Files.exists(filePath);
    }

    @Override
    public boolean isDirectory(final Path filePath) {
        return Files.isDirectory(filePath);
    }

    @Override
    public boolean isSameFile(final Path path1, final Path path2) throws IOException {
        return Files.isSameFile(path1, path2);
    }

    @Override
    public Stream<Path> list(final Path directoryPath) throws IOException {
        return Files.list(directoryPath);
    }

    @Override
    public Path move(final Path sourcePath, final Path targetPath, final CopyOption... options) throws IOException {
        return Files.move(sourcePath, targetPath, options);
    }

    @Override
    public BufferedReader newBufferedReader(final Path filePath) throws IOException {
        return Files.newBufferedReader(filePath);
    }

    @Override
    public InputStream newInputStream(Path filePath) throws IOException {
        return Files.newInputStream(filePath);
    }

    @Override
    public OutputStream newOutputStream(Path filePath) throws IOException {
        return Files.newOutputStream(filePath);
    }

    @Override
    public void moveFileTree(final Path sourcePath, final Path targetPath) throws IOException {
        copyFileTree(sourcePath, targetPath);
        deleteFileTree(sourcePath, true);
    }

    @Override
    public String readString(final Path path, final Charset charset) throws IOException {
        return Files.readString(path, charset);
    }

    @Override
    public boolean setFileExecutable(final Path path, final Platform platform) throws IOException {
        final boolean touched;
        if (!platform.isWindowsOs() && Files.exists(path) && !Files.isExecutable(path)) {
            touched = setFileExecutable(path, Files.getPosixFilePermissions(path), platform);
        } else {
            touched = false;
        }
        return touched;
    }

    @Override
    public Path setPosixFilePermissions(final Path path, final Set<PosixFilePermission> permissions)
        throws IOException {
        return Files.setPosixFilePermissions(path, permissions);
    }

    @Override
    public Path writeString(final Path path, final CharSequence charSequence, final Charset charset,
        final OpenOption... openOptions) throws IOException {
        return Files.writeString(path, charSequence, charset, openOptions);
    }

    /**
     * Marks the file as executable. This method does nothing under Windows. This method allows to restore a file's
     * permissions by providing the original permissions, in case they cannot be retrieved.
     *
     * @param path Path.
     * @param originalPermissions Original file permissions.
     * @param platform Underlying platform.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    private boolean setFileExecutable(final Path path, final Set<PosixFilePermission> originalPermissions,
        final Platform platform) throws IOException {
        final boolean touched;
        if (!platform.isWindowsOs() && Files.exists(path) && !Files.isExecutable(path)) {
            final Set<PosixFilePermission> newPermissions = EnumSet.copyOf(originalPermissions);
            newPermissions.add(PosixFilePermission.OWNER_EXECUTE);
            Files.setPosixFilePermissions(path, newPermissions);
            touched = true;
        } else {
            touched = false;
        }
        return touched;
    }

    /**
     * A visitor of paths that deletes the corresponding file and/or directory after all child files are deleted.
     */
    public static class FileDeleteVisitor extends SimpleFileVisitor<Path> {

        private final Path rootPath;

        private final boolean deleteRootEnabled;

        FileDeleteVisitor(final Path rootPath, final boolean deleteRootEnabled) {
            this.rootPath = rootPath;
            this.deleteRootEnabled = deleteRootEnabled;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path directory, final IOException e) throws IOException {
            if (e == null) {
                if (deleteRootEnabled || !directory.equals(rootPath)) {
                    Files.delete(directory);
                }
                return FileVisitResult.CONTINUE;
            } else {
                throw e;
            }
        }
    }

    /**
     * A visitor of paths that copies the corresponding file and/or directory before all child files are copied.
     */
    public static class FileCopyVisitor extends SimpleFileVisitor<Path> {

        private final Path rootPath;

        private final Path targetPath;

        FileCopyVisitor(final Path rootPath, final Path targetPath) {
            this.rootPath = rootPath;
            this.targetPath = targetPath;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path file, BasicFileAttributes basicFileAttributes)
            throws IOException {
            Files.copy(file, targetPath.resolve(rootPath.relativize(file)).normalize(), COPY_ATTRIBUTES,
                NOFOLLOW_LINKS);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attributes) throws IOException {
            Files.copy(file, targetPath.resolve(rootPath.relativize(file)).normalize(), COPY_ATTRIBUTES,
                NOFOLLOW_LINKS);
            return FileVisitResult.CONTINUE;
        }
    }
}
