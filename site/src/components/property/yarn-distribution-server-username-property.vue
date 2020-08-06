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
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-yarn-distribution-server-username-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
