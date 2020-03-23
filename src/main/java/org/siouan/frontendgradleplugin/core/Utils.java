package org.siouan.frontendgradleplugin.core;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class provides utilities for the whole plugin.
 */
public final class Utils {

    private Utils() {
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
     * Gets the extension of a file.
     *
     * @param fileName File name.
     * @return Extension.
     */
    public static Optional<String> getExtension(final String fileName) {
        final int index = fileName.lastIndexOf('.');
        final String extension;
        if (index == -1) {
            extension = null;
        } else {
            extension = fileName.substring(fileName.lastIndexOf('.'));
        }
        return Optional.ofNullable(extension);
    }

    /**
     * Gets the path of the Node executable.
     *
     * @param nodeInstallDirectory Node install directory.
     * @param osName O/S name.
     * @return The path, may be {@code null} if it was not found.
     * @see #getSystemOsName()
     */
    public static Optional<Path> getNodeExecutablePath(final Path nodeInstallDirectory, final String osName) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (isWindowsOs(osName)) {
            possiblePaths.add(nodeInstallDirectory.resolve("node.exe"));
            possiblePaths.add(nodeInstallDirectory.resolve("node.cmd"));
        } else {
            possiblePaths.add(nodeInstallDirectory.resolve("bin").resolve("node"));
        }

        return possiblePaths.stream().filter(Files::exists).findFirst();
    }

    /**
     * Gets the path of the NPM executable.
     *
     * @param nodeInstallDirectory Node install directory.
     * @param osName O/S name.
     * @return The path, may be {@code null} if it was not found.
     * @see #getSystemOsName()
     */
    public static Optional<Path> getNpmExecutablePath(final Path nodeInstallDirectory, final String osName) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (isWindowsOs(osName)) {
            possiblePaths.add(nodeInstallDirectory.resolve("npm.cmd"));
        } else {
            possiblePaths.add(nodeInstallDirectory.resolve("bin").resolve("npm"));
        }

        return possiblePaths.stream().filter(Files::exists).findFirst();
    }

    /**
     * Gets the current JVM architecture.
     *
     * @return String describing the JVM architecture.
     */
    public static String getSystemJvmArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    public static String getSystemOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets the path of Yarn executable.
     *
     * @param yarnInstallDirectory Yarn install directory.
     * @param osName O/S name.
     * @return The path, may be {@code null} if it was not found.
     * @see #getSystemOsName()
     */
    public static Optional<Path> getYarnExecutablePath(final Path yarnInstallDirectory, final String osName) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (isWindowsOs(osName)) {
            possiblePaths.add(yarnInstallDirectory.resolve("bin").resolve("yarn.cmd"));
        } else {
            possiblePaths.add(yarnInstallDirectory.resolve("bin").resolve("yarn"));
        }

        return possiblePaths.stream().filter(Files::exists).findFirst();
    }

    /**
     * Tells whether the given JRE architecture is a 64 bits one.
     *
     * @param jreArch JRE architecture.
     * @return {@code true} if the architecture is a 64 bits.
     * @see #getSystemJvmArch()
     */
    public static boolean is64BitsArch(final String jreArch) {
        final String jreArchLowered = jreArch.toLowerCase();
        return jreArchLowered.contains("x64") || jreArchLowered.contains("x86_64") || jreArchLowered.contains("amd64")
            || jreArchLowered.contains("ppc") || jreArchLowered.contains("sparc");
    }

    /**
     * Tells whether a file name extension is related to a GZIP file.
     *
     * @param extension Extension
     * @return {@code true} if the extension is related to a GZIP file.
     */
    public static boolean isGzipExtension(final String extension) {
        return extension.equals(".gz") || extension.equals(".gzip");
    }

    /**
     * Tells whether the given OS name matches a Linux OS.
     *
     * @param osName OS name.
     * @return {@code true} if the OS name matches a Linux OS.
     * @see #getSystemOsName()
     */
    public static boolean isLinuxOs(final String osName) {
        return osName.toLowerCase().contains("linux");
    }

    /**
     * Tells whether the given OS name matches a Mac OS.
     *
     * @param osName OS name.
     * @return {@code true} if the OS name matches a Mac OS.
     * @see #getSystemOsName()
     */
    public static boolean isMacOs(final String osName) {
        return osName.toLowerCase().contains("mac os");
    }

    /**
     * Tells whether the given OS name matches a Windows OS.
     *
     * @param osName OS name.
     * @return {@code true} if the OS name matches a Windows OS.
     * @see #getSystemOsName()
     */
    public static boolean isWindowsOs(final String osName) {
        return osName.toLowerCase().contains("windows");
    }

    /**
     * Removes the extension of a filename. In case of a compressed TAR archive, the method removes the whole extension
     * (e.g. '.tar.gz').
     *
     * @param filename Filename.
     * @return The filename without the extension.
     */
    public static String removeExtension(final String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    /**
     * Marks the file as executable. This method does nothing under Windows.
     *
     * @param path Path.
     * @param osName OS name.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean setFileExecutable(final Path path, final String osName) throws IOException {
        final boolean touched;
        if (!Utils.isWindowsOs(osName) && Files.exists(path) && !Files.isExecutable(path)) {
            touched = setFileExecutable(path, Files.getPosixFilePermissions(path), osName);
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
     * @param osName OS name.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean setFileExecutable(final Path path, final Set<PosixFilePermission> originalPermissions,
        final String osName) throws IOException {
        final boolean touched;
        if (!Utils.isWindowsOs(osName) && Files.exists(path) && !Files.isExecutable(path)) {
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
     * Converts a binary buffer into an hexadecimal string, with a lower case.
     *
     * @param buffer Buffer.
     * @return Hexadecimal string.
     */
    public static String toHexadecimalString(final byte[] buffer) {
        final StringBuilder hexadecimalString = new StringBuilder();
        for (byte digit : buffer) {
            String hexadecimalDigit = Integer.toHexString(0xff & digit);
            if (hexadecimalDigit.length() == 1) {
                hexadecimalString.append(0);
            }
            hexadecimalString.append(hexadecimalDigit);
        }
        return hexadecimalString.toString();
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
