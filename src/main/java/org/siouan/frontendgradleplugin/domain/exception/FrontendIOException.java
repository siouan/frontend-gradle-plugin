package org.siouan.frontendgradleplugin.domain.exception;

import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * Exception thrown when an operation failed due to an {@link IOException}.
 */
public class FrontendIOException extends FrontendException {

    private final IOErrorCode ioErrorCode;

    public FrontendIOException(@Nonnull final IOErrorCode ioErrorCode, @Nonnull final String message,
        @Nonnull final IOException cause) {
        super(message, cause);
        this.ioErrorCode = ioErrorCode;
    }

    @Nonnull
    public IOErrorCode getIoErrorCode() {
        return ioErrorCode;
    }
}
