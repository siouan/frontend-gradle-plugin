package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Exception thrown when an executable cannot be found.
 *
 * @since 1.1.2
 */
public class ExecutableNotFoundException extends FrontendException {

    private ExecutableNotFoundException(@Nonnull final String executableType) {
        super(executableType);
    }

    /**
     * Builds an exception when the {@code node} executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newNodeExecutableNotFoundException() {
        return new ExecutableNotFoundException(ExecutableType.NODE);
    }

    /**
     * Builds an exception when the {@code npm} executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newNpmExecutableNotFoundException() {
        return new ExecutableNotFoundException(ExecutableType.NPM);
    }

    /**
     * Builds an exception when the {@code npx} executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newNpxExecutableNotFoundException() {
        return new ExecutableNotFoundException(ExecutableType.NPX);
    }

    /**
     * Builds an exception when the {@code yarn} executable cannot be found.
     *
     * @return Exception.
     */
    @Nonnull
    public static ExecutableNotFoundException newYarnExecutableNotFoundException() {
        return new ExecutableNotFoundException(ExecutableType.YARN);
    }
}
