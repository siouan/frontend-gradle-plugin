package org.siouan.frontendgradleplugin.domain.provider;

import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Provider of loggers.
 *
 * @since 1.4.2
 */
public interface LoggerProvider {

    /**
     * Gets a logger.
     *
     * @return Logger.
     */
    Logger getLogger();
}
