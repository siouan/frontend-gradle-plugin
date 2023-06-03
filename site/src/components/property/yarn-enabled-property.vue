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
        <p>
            Whether <fgp-code>yarn</fgp-code> executable shall be used instead of <fgp-code>npm</fgp-code> executable
            when executing frontend tasks. Consequently, a <fgp-yarn-link /> distribution is required, and the plugin
            will locate it depending on the value of the <fgp-property-link name="yarnDistributionProvided" /> property.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';
import fgpYarnLink from '@/components/link/yarn-link';

export default Vue.component('fgp-yarn-enabled-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpYarnLink }
});
</script>
