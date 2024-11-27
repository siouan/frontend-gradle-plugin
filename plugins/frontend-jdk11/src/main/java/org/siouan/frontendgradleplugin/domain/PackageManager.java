package org.siouan.frontendgradleplugin.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Model class providing the content of the
 * <a href="https://nodejs.org/api/packages.html#packagemanager">packageManager</a> property located in a
 * {@code package.json} file.
 *
 * @since 7.0.0
 */
@Builder
@Getter
public class PackageManager {

    private final PackageManagerType type;

    private final String version;
}
