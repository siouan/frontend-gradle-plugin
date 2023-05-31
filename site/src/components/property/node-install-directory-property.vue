<template>
    <fgp-property
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="true"
        default-value='file("${projectDir}/node")'
        :tasks="['installNode', 'resolvePackageManager', 'installPackageManager', 'installFrontend', 'cleanFrontend', 'assembleFrontend', 'checkFrontend', 'publishFrontend']"
    >
        <p>
            Directory where the downloaded distribution shall be installed, or where a provided distribution is located
            if the <fgp-property-link name="nodeDistributionProvided" /> property is <fgp-code>true</fgp-code>.
        </p>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-install-directory-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
