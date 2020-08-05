<template>
    <fgp-property
        name="nodeVersion"
        type="java.lang.String"
        :required="false"
        :default-value="null"
        example="12.16.3"
        :tasks="['installNode']"
    >
        The version number is used to build the exact URL to download the distribution, as long as the
        <fgp-property-link name="nodeDistributionUrlPathPattern" /> property contains the
        <fgp-code>VERSION</fgp-code> token. The property is required when the
        <fgp-property-link name="nodeDistributionProvided" /> property is <fgp-code>false</fgp-code>.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-version-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
