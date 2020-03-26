package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when an executable cannot be found.
 *
 * @since 1.1.2
 */
public class ExecutableNotFoundException extends FrontendException {

    public static final String NODE = "Node";

    public static final String NPM = "NPM";

    public static final String YARN = "Yarn";

    private ExecutableNotFoundException(@Nonnull final String executable) {
        super(executable);
    }

    /**
     * Builds an exception when the Node executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newNodeExecutableNotFoundException() {
        return new ExecutableNotFoundException(NODE);
    }

    /**
     * Builds an exception when the NPM executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newNpmExecutableNotFoundException() {
        return new ExecutableNotFoundException(NPM);
    }

    /**
     * Builds an exception when the Yarn executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newYarnExecutableNotFoundException() {
        return new ExecutableNotFoundException(YARN);
    }
}
