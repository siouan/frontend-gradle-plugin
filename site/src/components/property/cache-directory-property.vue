<template>
    <fgp-property
        name="cacheDirectory"
        type="java.io.File"
        :required="true"
        default-value='file("${buildDir}/frontend-gradle-plugin")'
        :tasks="['resolvePackageManager', 'installPackageManager']"
    >
        <p>
            Directory where the plugin stores outputs of some of its tasks. Writing outputs in files is the only way to
            get Gradle skip these tasks when their inputs did not change, and reuse the outputs already computed from
            previous executions.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-cache-directory-property', {
    components: { fgpProperty }
});
</script>
