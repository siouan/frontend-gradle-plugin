<template>
    <fgp-property
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value="${projectDir}/node"
        :tasks="['installNode', 'resolvePackageManager', 'installPackageManager', 'installFrontend', 'cleanFrontend', 'assembleFrontend', 'checkFrontend', 'publishFrontend']"
    >
        <p>
            Directory where the <fgp-nodejs-link /> distribution shall be installed or is already installed (depending
            on the value of property <fgp-property-link name="nodeDistributionProvided" />). Despite its default
            <fgp-code>null</fgp-code> value, when property <fgp-property-link name="nodeDistributionProvided" /> is
            <fgp-code>false</fgp-code> or the <fgp-code>FGP_NODEJS_HOME</fgp-code> environment variable is undefined,
            the distribution is installed or expected to be found in directory <fgp-code>${projectDir}/node</fgp-code>.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-node-install-directory-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
