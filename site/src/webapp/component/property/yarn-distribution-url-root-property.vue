<template>
    <fgp-property
        name="yarnDistributionUrlRoot"
        type="java.lang.String"
        default-value="https://github.com/yarnpkg/yarn/releases/download/"
        :tasks="['installYarn']"
    >
        This property is used to build the exact URL to download the distribution, by appending the value of the
        <fgp-code>yarnDistributionUrlPathPattern</fgp-code> property to its value. By default, the plugin attempts to
        download the distribution from the <fgp-yarn-link /> website.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";

export default Vue.component("fgp-yarn-distribution-url-root-property", {
    components: { fgpCode, fgpProperty }
});
</script>
