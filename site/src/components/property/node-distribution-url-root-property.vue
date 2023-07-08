<template>
    <fgp-property
        name="nodeDistributionUrlRoot"
        type="java.lang.String"
        default-value="https://nodejs.org/dist/"
        :tasks="['installNode']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending the value of the
            <fgp-property-link name="nodeDistributionUrlPathPattern" /> property to its value. By default, the plugin attempts
            to download the distribution from the <fgp-nodejs-link /> website.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpPropertyLink from '@/components/link/property-link';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-node-distribution-url-root-property', {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
