package org.siouan.frontendgradleplugin.domain.util;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;

import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * This class provides utilities for file management.
 *
 * @since 1.4.2
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Deletes a file or a directory at the given path. If the root path is a directory, its content is deleted
     * recursively before the directory itself.
     *
     * @param rootPath Root path.
     * @param deleteRootEnabled Whether the file at the root path itself must also be deleted.
     * @throws IOException If an I/O error occurs.
     */
    public static void deleteFileTree(final Path rootPath, final boolean deleteRootEnabled) throws IOException {
        if (Files.exists(rootPath)) {
            Files.walkFileTree(rootPath, new FileDeleteVisitor(rootPath, deleteRootEnabled));
        }
    }

    /**
     * Copies a file at the given path, recursively if it is a directory. File attributes and symlinks are preserved.
     *
     * @param sourcePath Source path.
     * @param targetPath Target Path.
     * @throws IOException If an I/O error occurs.
     */
    public static void copyFileTree(final Path sourcePath, final Path targetPath) throws IOException {
        Files.walkFileTree(sourcePath, new FileCopyVisitor(sourcePath, targetPath));
    }

    /**
     * Moves all files/directories from a source directory into a destination directory. The destination directory is
     * created, and therefore must not exist before this method is called. All directories/files in the source path are
     * copied first, preserving file attributes and symlinks. Finally the source path is deleted. Such method ensures
     * any file tree can be moved from a volume to another.
     *
     * @param sourcePath Source path.
     * @param targetPath Target path.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalArgumentException If either the source directory or the destination directory is not an existing
     * directory.
     */
    public static void moveFileTree(final Path sourcePath, final Path targetPath) throws IOException {
        copyFileTree(sourcePath, targetPath);
        deleteFileTree(sourcePath, true);
    }

    /**
     * Marks the file as executable. This method does nothing under Windows.
     *
     * @param path Path.
     * @param platform Execution platform.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean setFileExecutable(final Path path, final Platform platform) throws IOException {
        final boolean touched;
        if (!platform.isWindowsOs() && Files.exists(path) && !Files.isExecutable(path)) {
            touched = setFileExecutable(path, Files.getPosixFilePermissions(path), platform);
        } else {
            touched = false;
        }
        return touched;
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
    public static boolean setFileExecutable(final Path path, final Set<PosixFilePermission> originalPermissions,
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
