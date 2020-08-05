<template>
    <fgp-property
        name="yarnDistributionServerUsername"
        type="java.lang.String"
        :required="false"
        :tasks="['installYarn']"
    >
        This property may be used to download the distribution with a given identity (BASIC scheme server-side). When
        not <fgp-code>null</fgp-code>, the <fgp-property-link name="yarnDistributionServerPassword" /> property is also
        required.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-yarn-distribution-server-username-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
