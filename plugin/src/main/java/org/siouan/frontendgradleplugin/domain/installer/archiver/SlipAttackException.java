package org.siouan.frontendgradleplugin.domain.installer.archiver;

/**
 * Exception thrown when an archive contains an entry that would cause writing out of the target directory (e.g.
 * '../../evil.sh') during exploding. This vulnerability is known as the
 * <a href="https://snyk.io/research/zip-slip-vulnerability">Zip Slip Vulnerability</a>.
 *
 * @since 1.1.2
 */
public class SlipAttackException extends ArchiverException {

    /**
     * Builds an exception with the given entry name.
     *
     * @param entryName Entry name.
     */
    public SlipAttackException(final String entryName) {
        super("Slip attack detected: cannot explode entry '" + entryName + "' out of target directory");
    }
}
