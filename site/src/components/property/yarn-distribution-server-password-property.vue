<template>
    <fgp-property
        name="yarnDistributionServerPassword"
        type="java.lang.String"
        :required="false"
        :tasks="['installYarn']"
    >
        <p>
            Set this property to authenticate the distribution download (BASIC scheme server-side). The property is
            ignored unless the <fgp-property-link name="yarnDistributionServerUsername" /> property is defined.
        </p>
        <fgp-warning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";
import fgpWarning from "../warning";

export default Vue.component("fgp-yarn-distribution-server-password-property", {
    components: { fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
