package org.siouan.frontendgradleplugin.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Interface of a file manager, providing read/write access to file systems.
 *
 * @since 2.0.0
 */
public interface FileManager {

    /**
     * Copies all bytes from an input stream into a file.
     *
     * @param inputStream Input stream.
     * @param filePath File path.
     * @return Number of bytes read/written.
     * @throws IOException If an I/O error occurs.
     */
    long copy(InputStream inputStream, Path filePath) throws IOException;

    /**
     * Copies a file at the given path, recursively if it is a directory. File attributes and symlinks are preserved.
     *
     * @param sourcePath Source path.
     * @param targetPath Target path.
     * @throws IOException If an I/O error occurs.
     */
    void copyFileTree(Path sourcePath, Path targetPath) throws IOException;

    /**
     * Creates the directory at the given path and all intermediate directories that may not exist.
     *
     * @param path Path.
     * @return The same path.
     * @throws IOException If an I/O error occurs.
     */
    Path createDirectories(Path path) throws IOException;

    /**
     * Creates the directory at the given path. All intermediate directories must exist.
     *
     * @param path Path.
     * @return The same path.
     * @throws IOException If an I/O error occurs.
     */
    Path createDirectory(Path path) throws IOException;

    /**
     * Creates a symbolic link.
     *
     * @param linkFilePath Path to the symbolic link.
     * @param targetFilePath Path to the target of the symbolic link.
     * @return The link file path.
     * @throws IOException If an I/O error occurs.
     */
    Path createSymbolicLink(Path linkFilePath, Path targetFilePath) throws IOException;

    /**
     * Deletes the file or directory at the given path. The file/directory must exist.
     *
     * @param path File path.
     * @throws IOException If an I/O error occurs.
     */
    void delete(Path path) throws IOException;

    /**
     * Deletes the file or directory at the given path, if it exists.
     *
     * @param path File path.
     * @return {@code true} if the file existed and was deleted.
     * @throws IOException If an I/O error occurs.
     */
    boolean deleteIfExists(Path path) throws IOException;

    /**
     * Deletes a file or a directory at the given path. If the root path is a directory, its content is deleted
     * recursively before the directory itself.
     *
     * @param rootPath Root path.
     * @param deleteRootEnabled Whether the file at the root path itself must also be deleted.
     * @throws IOException If an I/O error occurs.
     */
    void deleteFileTree(Path rootPath, boolean deleteRootEnabled) throws IOException;

    /**
     * Whether a file/directory/symbolic link at the given path exists.
     *
     * @param filePath File path.
     * @return {@code true} if a file exists.
     */
    boolean exists(Path filePath);

    /**
     * Whether the file at the given path exists and is a directory.
     *
     * @param filePath path.
     * @return {@code true} if the file is a directory.
     */
    boolean isDirectory(Path filePath);

    /**
     * Whether the given path points to the same file.
     *
     * @param path1 First path.
     * @param path2 Second path.
     * @return {@code true} if both paths point to the same file.
     * @throws IOException If an I/O error occurs.
     */
    boolean isSameFile(Path path1, Path path2) throws IOException;

    /**
     * Gets the paths to the files located under the directory at the given path.
     *
     * @param directoryPath Path to a directory.
     * @return Stream of paths (must be closed after usage).
     * @throws IOException If an I/O error occurs.
     */
    Stream<Path> list(Path directoryPath) throws IOException;

    /**
     * Moves/renames a file at the given source path into the given target path.
     *
     * @param sourcePath Path to the source file.
     * @param targetPath Path to the target file.
     * @param options Copy options.
     * @return Path to the target file.
     * @throws IOException If an I/O error occurs.
     */
    Path move(Path sourcePath, Path targetPath, CopyOption... options) throws IOException;

    /**
     * Opens the file at the given path for reading through a buffer.
     *
     * @param filePath File path.
     * @return Buffered reader.
     * @throws IOException If an I/O error occurs.
     */
    BufferedReader newBufferedReader(Path filePath) throws IOException;

    /**
     * Opens a file at the given path for reading.
     *
     * @param filePath File path.
     * @return Input stream.
     * @throws IOException If an I/O error occurs.
     */
    InputStream newInputStream(Path filePath) throws IOException;

    /**
     * Opens or creates a file at the given path for writing.
     *
     * @param filePath File path.
     * @return Output stream.
     * @throws IOException If an I/O error occurs.
     */
    OutputStream newOutputStream(Path filePath) throws IOException;

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
    void moveFileTree(Path sourcePath, Path targetPath) throws IOException;

    /**
     * Reads and return the entire content of a file, decoding from bytes to characters using the specified charset.
     *
     * @param path File path.
     * @param charset Decoding charset.
     * @return Content.
     * @throws IOException If an I/O error occurs.
     */
    String readString(Path path, Charset charset) throws IOException;

    /**
     * Marks the file as executable. This method does nothing under Windows.
     *
     * @param path Path.
     * @param platform Underlying platform.
     * @return {@code true} if the file permissions were touched, i.e. under a Non-Windows O/S, the file exists and has
     * not the executable permission yet.
     * @throws IOException If an I/O error occurs.
     */
    boolean setFileExecutable(Path path, Platform platform) throws IOException;

    /**
     * Sets the POSIX permissions of the file/directory at the given path.
     *
     * @param path Path.
     * @param permissions POSIX permissions.
     * @return The same path.
     * @throws IOException If an I/O error occurs.
     */
    Path setPosixFilePermissions(Path path, Set<PosixFilePermission> permissions) throws IOException;

    /**
     * Writes the content of a file, as a character sequence encoded into bytes using the specified charset.
     *
     * @param path File path.
     * @param charSequence Content.
     * @param charset Encoding charset.
     * @param openOptions Options specifying how the file is opened.
     * @return The same path.
     * @throws IOException If an I/O error occurs.
     */
    Path writeString(Path path, CharSequence charSequence, Charset charset, OpenOption... openOptions)
        throws IOException;
}
