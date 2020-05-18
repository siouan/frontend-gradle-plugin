<template>
    <fgp-property
        name="yarnInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value='file("${projectDir}/yarn")'
        :tasks="['installYarn']"
    >
        Directory where the downloaded distribution shall be installed, or where a provided distribution is located if
        the <fgp-property-link name="yarnDistributionProvided" /> property is <fgp-code>true</fgp-code>.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-yarn-install-directory-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
