<template>
    <fgp-property
        name="nodeDistributionUrlRoot"
        type="java.lang.String"
        default-value="https://nodejs.org/dist/"
        :tasks="['installNode']"
    >
        This property is used to build the exact URL to download the distribution, by appending the value of the
        <fgp-code>nodeDistributionUrlPathPattern</fgp-code> property to its value. By default, the plugin attempts to
        download the distribution from the <fgp-nodejs-link /> website.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";

export default Vue.component("fgp-node-distribution-url-root-property", {
    components: { fgpCode, fgpProperty }
});
</script>
