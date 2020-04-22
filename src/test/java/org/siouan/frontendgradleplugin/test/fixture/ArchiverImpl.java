package org.siouan.frontendgradleplugin.test.fixture;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.model.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.usecase.AbstractArchiver;

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
        this.entries = Collections.unmodifiableCollection(entries).iterator();
        this.initException = initException;
        this.writeException = writeException;
        this.writtenFilePaths = new HashMap<>();
    }

    @Nonnull
    @Override
    protected ArchiverContext initializeContext(@Nonnull final ExplodeSettings settings) throws ArchiverException {
        if (initException == null) {
            return context;
        } else {
            throw initException;
        }
    }

    @Nonnull
    @Override
    protected Optional<ArchiveEntryImpl> getNextEntry(@Nonnull final ArchiverContext context) {
        return Optional.ofNullable(entries.hasNext() ? entries.next() : null);
    }

    @Nonnull
    public Map<Path, String> getWrittenFilePaths() {
        return Collections.unmodifiableMap(writtenFilePaths);
    }

    @Nonnull
    @Override
    protected String getSymbolicLinkTarget(@Nonnull final ArchiverContext context,
        @Nonnull final ArchiveEntryImpl entry) {
        return entry.getContent();
    }

    @Override
    protected void writeRegularFile(@Nonnull final ArchiverContext context, @Nonnull final ArchiveEntryImpl entry,
        @Nonnull final Path filePath) throws IOException {
        if (writeException == null) {
            writtenFilePaths.put(filePath, entry.getContent());
        } else {
            throw writeException;
        }
    }
}
