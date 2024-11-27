package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParsePackageManagerSpecificationTest {

    @InjectMocks
    private ParsePackageManagerSpecification usecase;

    @Test
    void should_fail_when_specification_is_malformed() {
        assertThatThrownBy(() -> usecase.execute("invalid")).isInstanceOf(MalformedPackageManagerSpecification.class);
    }

    @Test
    void should_fail_when_package_manager_is_unknown() {
        assertThatThrownBy(() -> usecase.execute("name@4.9.6")).isInstanceOf(UnsupportedPackageManagerException.class);
    }

    @Test
    void should_return_package_manager_specification_when_no_hash_is_present()
        throws MalformedPackageManagerSpecification, UnsupportedPackageManagerException {

        assertThat(usecase.execute("npm@10.9.0")).satisfies(packageManager -> {
            assertThat(packageManager.getType()).isEqualTo(PackageManagerType.NPM);
            assertThat(packageManager.getVersion()).isEqualTo("10.9.0");
        });
    }

    @Test
    void should_return_package_manager_specification_when_hash_is_present()
        throws MalformedPackageManagerSpecification, UnsupportedPackageManagerException {

        assertThat(usecase.execute(
            "npm@10.9.1+sha512.c89530d37c4baa38afd43e76a077a84b9aa63840b986426584fd5c5a54ab0a0b21bb1595c851042b733784b0b43706d36a494b4d8ae1a086a762cb8d3f95942a")).satisfies(
            packageManager -> {
                assertThat(packageManager.getType()).isEqualTo(PackageManagerType.NPM);
                assertThat(packageManager.getVersion()).isEqualTo("10.9.1");
            });
    }
}
