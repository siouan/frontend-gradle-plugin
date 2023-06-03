<template>
    <fgp-property
        name="nodeDistributionServerPassword"
        type="java.lang.String"
        :required="false"
        :tasks="['installNode']"
    >
        <p>
            Set this property to authenticate the distribution download (BASIC scheme server-side). The property is
            ignored if the <fgp-property-link name="nodeDistributionServerUsername" /> property is
            <fgp-code>null</fgp-code>.
        </p>
        <fgp-warning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';
import fgpWarning from '@/components/warning';

export default Vue.component('fgp-node-distribution-server-password-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
