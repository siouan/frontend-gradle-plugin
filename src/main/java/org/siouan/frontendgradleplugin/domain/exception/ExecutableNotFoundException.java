package org.siouan.frontendgradleplugin.domain.exception;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Exception thrown when an executable cannot be found.
 *
 * @since 1.1.2
 */
public class ExecutableNotFoundException extends FrontendException {

    public ExecutableNotFoundException(@Nonnull final Path executablePath) {
        super(executablePath.toString());
    }
}
