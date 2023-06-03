package org.siouan.frontendgradleplugin.domain.installer.archiver;

/**
 * Internal type of an entry.
 */
enum ArchiveEntryType {

    SYMBOLIC_LINK,
    DIRECTORY,
    FILE,
    UNKNOWN
}
