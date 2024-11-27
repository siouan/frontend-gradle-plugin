package org.siouan.frontendgradleplugin.domain.installer.archiver;

import static java.util.Collections.unmodifiableCollection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.FileManager;

/**
 * Test implementation of an archiver, that allows simulating various archive entries and I/O errors.
 */
public class ArchiverImpl extends AbstractArchiver<ArchiverContext, ArchiveEntryImpl> {

    private final ArchiverContext context;

    private final Iterator<ArchiveEntryImpl> entries;

    private final ArchiverException initException;

    private final IOException writeException;

    private final Map<Path, String> writtenFilePaths;

    public ArchiverImpl(final FileManager fileManager, final ArchiverContext context,
        final Collection<ArchiveEntryImpl> entries) {
        this(fileManager, context, entries, null, null);
    }

    public ArchiverImpl(final FileManager fileManager, final ArchiverContext context,
        final Collection<ArchiveEntryImpl> entries, final ArchiverException initException) {
        this(fileManager, context, entries, initException, null);
    }

    public ArchiverImpl(final FileManager fileManager, final ArchiverContext context,
        final Collection<ArchiveEntryImpl> entries, final IOException writeException) {
        this(fileManager, context, entries, null, writeException);
    }

    ArchiverImpl(final FileManager fileManager, final ArchiverContext context,
        final Collection<ArchiveEntryImpl> entries, final ArchiverException initException,
        final IOException writeException) {
        super(fileManager);
        this.context = context;
        this.entries = unmodifiableCollection(entries).iterator();
        this.initException = initException;
        this.writeException = writeException;
        this.writtenFilePaths = new HashMap<>();
    }

    @Override
    protected ArchiverContext initializeContext(final ExplodeCommand explodeCommand) throws ArchiverException {
        if (initException == null) {
            return context;
        } else {
            throw initException;
        }
    }

    @Override
    protected Optional<ArchiveEntryImpl> getNextEntry(final ArchiverContext context) {
        return Optional.ofNullable(entries.hasNext() ? entries.next() : null);
    }

    public Map<Path, String> getWrittenFilePaths() {
        return Map.copyOf(writtenFilePaths);
    }

    @Override
    protected String getSymbolicLinkTarget(final ArchiverContext context, final ArchiveEntryImpl entry) {
        return entry.getContent();
    }

    @Override
    protected void writeRegularFile(final ArchiverContext context, final ArchiveEntryImpl entry, final Path filePath)
        throws IOException {
        if (writeException == null) {
            writtenFilePaths.put(filePath, entry.getContent());
        } else {
            throw writeException;
        }
    }
}
