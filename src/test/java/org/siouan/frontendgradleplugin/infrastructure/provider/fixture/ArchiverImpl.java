package org.siouan.frontendgradleplugin.infrastructure.provider.fixture;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.model.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.infrastructure.archiver.AbstractArchiver;

/**
 * Test implementation of an archiver, that allows simulating various archive entries and I/O errors.
 */
public class ArchiverImpl extends AbstractArchiver<ArchiverContext, ArchiveEntryImpl> {

    private final ArchiverContext context;

    private final Iterator<ArchiveEntryImpl> entries;

    private final ArchiverException initException;

    private final IOException writeException;

    public ArchiverImpl(final ArchiverContext context, final Collection<ArchiveEntryImpl> entries) {
        this(context, entries, null, null);
    }

    public ArchiverImpl(final ArchiverContext context, final Collection<ArchiveEntryImpl> entries,
        final ArchiverException initException) {
        this(context, entries, initException, null);
    }

    ArchiverImpl(final ArchiverContext context, final Collection<ArchiveEntryImpl> entries,
        final IOException writeException) {
        this(context, entries, null, writeException);
    }

    ArchiverImpl(final ArchiverContext context, final Collection<ArchiveEntryImpl> entries,
        final ArchiverException initException, final IOException writeException) {
        this.context = context;
        this.entries = entries.iterator();
        this.initException = initException;
        this.writeException = writeException;
    }

    @Override
    protected ArchiverContext initializeContext(final ExplodeSettings settings) throws ArchiverException {
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

    @Override
    protected String getSymbolicLinkTarget(final ArchiverContext context, final ArchiveEntryImpl entry) {
        return entry.getData();
    }

    @Override
    protected void writeRegularFile(final ArchiverContext context, final ArchiveEntryImpl entry, final Path targetFile)
        throws IOException {
        if (writeException == null) {
            try (final PrintWriter writer = new PrintWriter(targetFile.toFile())) {
                writer.print(entry.getData());
            }
        } else {
            throw writeException;
        }
    }
}
