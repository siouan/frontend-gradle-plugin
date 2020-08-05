<template>
    <fgp-property
        name="yarnEnabled"
        type="boolean"
        default-value="false"
        :tasks="[
            'installYarn',
            'installFrontend',
            'cleanFrontend',
            'assembleFrontend',
            'checkFrontend',
            'publishFrontend'
        ]"
    >
        Whether <fgp-code>yarn</fgp-code> executable shall be used instead of <fgp-code>npm</fgp-code> executable when
        executing frontend tasks. Consequently, a <fgp-yarn-link /> distribution is required, and the plugin will locate
        it depending on the value of the <fgp-property-link name="yarnDistributionProvided" /> property.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-yarn-enabled-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpYarnLink }
});
</script>
