<template>
    <fgp-property name="verboseModeEnabled" type="boolean" default-value="false" :tasks="[]">
        <p>
            Whether the plugin shall log additional messages whatever Gradle's logging level is. Technically speaking,
            messages logged by the plugin with the <fgp-code>INFO</fgp-code> level are made visible. This property
            allows to track the plugin execution without activating Gradle's <fgp-code>INFO</fgp-code> or
            <fgp-code>DEBUG</fgp-code> levels, that may be too much verbose on a global point of view.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-verbose-mode-enabled-property', {
    components: { fgpCode, fgpProperty }
});
</script>
