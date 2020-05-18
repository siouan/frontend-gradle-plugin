<template>
    <fgp-property
        name="nodeDistributionServerPassword"
        type="java.lang.String"
        :required="false"
        :tasks="['installNode']"
    >
        Set this property to authenticate the distribution download (BASIC scheme server-side). The property is ignored
        if the <fgp-property-link name="nodeDistributionServerUsername" /> property is <fgp-code>null</fgp-code>.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-distribution-server-password-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
