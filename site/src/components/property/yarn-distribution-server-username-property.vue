<template>
    <fgp-property
        name="yarnDistributionServerUsername"
        type="java.lang.String"
        :required="false"
        :tasks="['installYarn']"
    >
        <p>
            This property may be used to download the distribution with a given identity (BASIC scheme server-side).
            When not <fgp-code>null</fgp-code>, the <fgp-property-link name="yarnDistributionServerPassword" /> property
            is also required.
        </p>
        <fgp-warning>
            SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";
import fgpWarning from "../warning";

export default Vue.component("fgp-yarn-distribution-server-username-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
