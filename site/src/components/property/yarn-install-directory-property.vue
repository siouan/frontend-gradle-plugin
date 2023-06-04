<template>
    <fgp-property
        name="yarnInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value='file("${projectDir}/yarn")'
        :tasks="['installYarn']"
    >
        <p>
            Directory where the downloaded distribution shall be installed, or where a provided distribution is located
            if the <fgp-property-link name="yarnDistributionProvided" /> property is <fgp-code>true</fgp-code>.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-yarn-install-directory-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
