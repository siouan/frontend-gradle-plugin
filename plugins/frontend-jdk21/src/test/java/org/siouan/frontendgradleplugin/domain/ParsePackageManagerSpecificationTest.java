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
        assertThatThrownBy(() -> usecase.execute("name@version")).isInstanceOf(
            UnsupportedPackageManagerException.class);
    }

    @Test
    void should_return_package_manager_specification_with_no_other_specification_present()
        throws MalformedPackageManagerSpecification, UnsupportedPackageManagerException {

        assertThat(usecase.execute("npm@10.9.0")).satisfies(packageManager -> {
            assertThat(packageManager.type()).isEqualTo(PackageManagerType.NPM);
            assertThat(packageManager.version()).isEqualTo("10.9.0");
        });
    }
}
