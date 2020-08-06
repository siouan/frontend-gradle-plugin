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
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-yarn-distribution-server-password-property", {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
