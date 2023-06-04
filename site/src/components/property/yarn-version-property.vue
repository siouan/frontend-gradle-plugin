<template>
    <fgp-property
        name="yarnVersion"
        type="java.lang.String"
        :required="false"
        example="3.5.1"
        :tasks="['installYarn']"
    >
        <p>
            The version number is used to build the exact URL to download the distribution, as long as the
            <fgp-property-link name="yarnDistributionUrlPathPattern" /> property contains the
            <fgp-code>VERSION</fgp-code> token. The property is required when the
            <fgp-property-link name="yarnEnabled" /> property is <fgp-code>true</fgp-code> and the
            <fgp-property-link name="yarnDistributionProvided" /> property is <fgp-code>false</fgp-code>.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-yarn-version-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
