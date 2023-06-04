<template>
    <fgp-property
        name="nodeVersion"
        type="java.lang.String"
        :required="false"
        :default-value="null"
        example="18.16.0"
        :tasks="['installNode']"
    >
        <p>
            The version number is used to build the exact URL to download the distribution. Though it is not required,
            it must be defined when
            <fgp-property-link name="nodeDistributionProvided" /> property is <fgp-code>false</fgp-code> and
            <fgp-property-link name="nodeDistributionUrlPathPattern" /> property contains the
            <fgp-code>VERSION</fgp-code> token.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-node-version-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
