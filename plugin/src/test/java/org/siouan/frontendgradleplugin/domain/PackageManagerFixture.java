package org.siouan.frontendgradleplugin.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PackageManagerFixture {

    public static PackageManager aPackageManager() {
        return PackageManager.builder().type(PackageManagerType.NPM).version("9.7.1").build();
    }
}
