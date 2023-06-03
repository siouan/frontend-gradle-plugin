<template>
    <fgp-property
        name="checkScript"
        type="java.lang.String"
        :required="false"
        example="run check"
        :tasks="['checkFrontend']"
    >
        <p>Script called to check the frontend application.</p>
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpInfo from '@/components/info';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-check-script-property', {
    components: { fgpInfo, fgpProperty }
});
</script>
