<template>
    <fgp-property
        name="cleanScript"
        type="java.lang.String"
        :required="false"
        example="run clean"
        :tasks="['cleanFrontend']"
    >
        <p>
            This property may be used to clean frontend's compiled artifacts, when they are generated out of the
            <fgp-code>${project.buildDir}</fgp-code> directory.
        </p>
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpInfo from '@/components/info';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-clean-script-property', {
    components: { fgpCode, fgpInfo, fgpProperty }
});
</script>
