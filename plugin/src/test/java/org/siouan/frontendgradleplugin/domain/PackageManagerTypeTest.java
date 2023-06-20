package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PackageManagerTypeTest {

    @Test
    void should_return_no_package_manager_type() {
        assertThat(PackageManagerType.fromPackageManagerName("NPM")).isEmpty();
    }

    @Test
    void should_return_npm_package_manager_type() {
        assertThat(PackageManagerType.fromPackageManagerName("npm")).contains(PackageManagerType.NPM);
    }

    @Test
    void should_return_pnpm_package_manager_type() {
        assertThat(PackageManagerType.fromPackageManagerName("pnpm")).contains(PackageManagerType.PNPM);
    }

    @Test
    void should_return_yarn_package_manager_type() {
        assertThat(PackageManagerType.fromPackageManagerName("yarn")).contains(PackageManagerType.YARN);
    }
}
