package org.siouan.frontendgradleplugin.domain.model;

/**
 * Enumeration of types of executable supported by the plugin.
 *
 * @since 1.2.0
 */
public final class ExecutableType {

    public static final String NODE = "node";

    public static final String NPM = "npm";

    public static final String NPX = "npx";

    public static final String YARN = "yarn";

    private ExecutableType() {
    }
}
