package org.siouan.frontendgradleplugin.core;

/**
 * Exception thrown when an executable cannot be found.
 */
public class ExecutableNotFoundException extends FrontendException {

    public static final String NODE = "Node";

    public static final String NPM = "NPM";

    public static final String YARN = "Yarn";

    private ExecutableNotFoundException(final String executable) {
        super(executable);
    }

    public static ExecutableNotFoundException newNodeExecutableNotFoundException() {
        return new ExecutableNotFoundException(NODE);
    }

    public static ExecutableNotFoundException newNpmExecutableNotFoundException() {
        return new ExecutableNotFoundException(NPM);
    }

    public static ExecutableNotFoundException newYarnExecutableNotFoundException() {
        return new ExecutableNotFoundException(YARN);
    }
}
