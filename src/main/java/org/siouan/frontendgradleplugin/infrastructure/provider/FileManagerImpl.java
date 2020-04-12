package org.siouan.frontendgradleplugin.infrastructure.provider;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Implementation that delegate calls to the JDK NIO {@link Files} class.
 *
 * @since 2.0.0
 */
public class FileManagerImpl implements FileManager {

    @Override
    public long copy(@Nonnull final InputStream inputStream, @Nonnull final Path filePath) throws IOException {
        return Files.copy(inputStream, filePath);
    }

    @Override
    public void copyFileTree(@Nonnull final Path sourcePath, @Nonnull final Path targetPath) throws IOException {
        Files.walkFileTree(sourcePath, new FileCopyVisitor(sourcePath, targetPath));
    }

    @Override
    @Nonnull
    public Path createDirectories(@Nonnull final Path path) throws IOException {
        return Files.createDirectories(path);
    }

    @Nonnull
    @Override
    public Path createDirectory(@Nonnull final Path directoryPath) throws IOException {
        return Files.createDirectory(directoryPath);
    }

    @Nonnull
    @Override
    public Path createSymbolicLink(@Nonnull final Path linkFilePath, @Nonnull final Path targetFilePath)
        throws IOException {
        return Files.createSymbolicLink(linkFilePath, targetFilePath);
    }

    @Override
    public void delete(@Nonnull final Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public boolean deleteIfExists(@Nonnull final Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    @Override
    public void deleteFileTree(@Nonnull final Path rootPath, final boolean deleteRootEnabled) throws IOException {
        if (Files.exists(rootPath)) {
            Files.walkFileTree(rootPath, new FileDeleteVisitor(rootPath, deleteRootEnabled));
        }
    }

    @Override
    public boolean exists(@Nonnull final Path filePath) {
        return Files.exists(filePath);
    }

    @Override
    public boolean isDirectory(@Nonnull final Path filePath) {
        return Files.isDirectory(filePath);
    }

    @Override
    public boolean isSameFile(@Nonnull final Path path1, @Nonnull final Path path2) throws IOException {
        return Files.isSameFile(path1, path2);
    }

    @Override
    @Nonnull
    public Stream<Path> list(@Nonnull final Path directoryPath) throws IOException {
        return Files.list(directoryPath);
    }

    @Override
    @Nonnull
    public Path move(@Nonnull final Path sourcePath, @Nonnull final Path targetPath) throws IOException {
        return Files.move(sourcePath, targetPath);
    }

    @Override
    @Nonnull
    public BufferedReader newBufferedReader(@Nonnull final Path filePath) throws IOException {
        return Files.newBufferedReader(filePath);
    }

    @Nonnull
    @Override
    public InputStream newInputStream(@Nonnull Path filePath) throws IOException {
        return Files.newInputStream(filePath);
    }

    @Nonnull
    @Override
    public OutputStream newOutputStream(@Nonnull Path filePath) throws IOException {
        return Files.newOutputStream(filePath);
    }

    @Override
    public void moveFileTree(@Nonnull final Path sourcePath, @Nonnull final Path targetPath) throws IOException {
        copyFileTree(sourcePath, targetPath);
        deleteFileTree(sourcePath, true);
    }

    @Override
    public boolean setFileExecutable(@Nonnull final Path path, @Nonnull final Platform platform) throws IOException {
        final boolean touched;
        if (!platform.isWindowsOs() && Files.exists(path) && !Files.isExecutable(path)) {
            touched = setFileExecutable(path, Files.getPosixFilePermissions(path), platform);
        } else {
            touched = false;
        }
        return touched;
    }

    @Override
    @Nonnull
    public Path setPosixFilePermissions(@Nonnull final Path path, @Nonnull final Set<PosixFilePermission> permissions)
        throws IOException {
        return Files.setPosixFilePermissions(path, permissions);
    }

    /**
     * Marks the file as executable. This method does nothing under Windows. This method allows to restore a file's
     * permissions by providing the original permissions, in case they cannot be retrieved.
     *
     * @param path Path.
     * @param originalPermissions Original file permissions.
     * @param platform Execution platform.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    private boolean setFileExecutable(@Nonnull final Path path,
        @Nonnull final Set<PosixFilePermission> originalPermissions, @Nonnull final Platform platform)
        throws IOException {
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
    private static class FileDeleteVisitor extends SimpleFileVisitor<Path> {

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
    private static class FileCopyVisitor extends SimpleFileVisitor<Path> {

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
