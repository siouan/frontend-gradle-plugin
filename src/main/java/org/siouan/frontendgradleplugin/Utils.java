package org.siouan.frontendgradleplugin;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * This class provides utilities for the whole plugin.
 */
public final class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    private Utils() {
    }

    /**
     * Closes a resource and produces a WARN log if it fails.
     *
     * @param closeable Closeable resource.
     */
    public static void closeOrWarn(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final IOException e) {
                LOGGER.log(Level.WARNING, "Resource not closed", e);
            }
        }
    }

    /**
     * Deletes a file at the given path, recursively if it is a directory.
     *
     * @param rootPath Root path.
     * @param deleteRootEnabled Whether the file at the root path itself must also be deleted.
     */
    public static void deleteRecursively(final Path rootPath, final boolean deleteRootEnabled) throws IOException {
        Files.walkFileTree(rootPath, new FileDeleteVisitor(rootPath, deleteRootEnabled));
    }

    public static boolean is64BitsArch(final String jreArch) {
        final String jreArchLowered = jreArch.toLowerCase();
        return jreArchLowered.contains("x64") || jreArchLowered.contains("amd64") || jreArchLowered.contains("ppc")
            || jreArchLowered.contains("sparc");
    }

    public static boolean isLinuxOs(final String osName) {
        return osName.toLowerCase().contains("linux");
    }

    public static boolean isMacOs(final String osName) {
        return osName.toLowerCase().contains("mac os");
    }

    public static boolean isWindowsOs(final String osName) {
        return osName.toLowerCase().contains("windows");
    }

    /**
     * Moves all files/directories from a source directory into a destination directory.
     *
     * @param srcDirectory Source directory.
     * @param destDirectory Destination directory.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalArgumentException If either the source directory or the destination directory is not an existing
     * directory.
     */
    public static void moveFiles(final File srcDirectory, final File destDirectory) throws IOException {
        if (!srcDirectory.isDirectory() || !destDirectory.isDirectory()) {
            throw new IllegalArgumentException();
        }

        try (final Stream<Path> childPaths = Files.list(srcDirectory.toPath())) {
            childPaths.forEach(path -> {
                try {
                    Files.move(path, new File(destDirectory, path.getFileName().toString()).toPath());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    /**
     * Removes the frontend of a filename. In case of a compressed TAR archive, the method removes the whole frontend
     * (e.g. '.tar.gz').
     *
     * @param filename Filename.
     * @return The filename without the frontend.
     */
    public static String removeExtension(final String filename) {
        final String filenameWithoutExtension;
        if (filename.endsWith(".tar.Z")) {
            filenameWithoutExtension = filename.substring(0, filename.lastIndexOf(".tar.Z"));
        } else {
            if (filename.endsWith(".tar.gz")) {
                filenameWithoutExtension = filename.substring(0, filename.lastIndexOf(".tar.gz"));
            } else {
                filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
            }
        }
        return filenameWithoutExtension;
    }

    /**
     * A visitor of paths that deletes the corresponding file and/or directory after all child files are deleted.
     */
    private static class FileDeleteVisitor extends SimpleFileVisitor<Path> {

        private final Path rootPath;

        private final boolean deleteRootEnabled;

        public FileDeleteVisitor(final Path rootPath, final boolean deleteRootEnabled) {
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
}
