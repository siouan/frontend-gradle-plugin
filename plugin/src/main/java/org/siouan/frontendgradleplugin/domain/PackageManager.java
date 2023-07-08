package org.siouan.frontendgradleplugin.domain;

import lombok.Builder;

/**
 * Model class providing the content of the
 * <a href="https://nodejs.org/api/packages.html#packagemanager">packageManager</a> property located in a
 * {@code package.json} file.
 *
 * @since 7.0.0
 */
@Builder
public record PackageManager(PackageManagerType type, String version) {}
