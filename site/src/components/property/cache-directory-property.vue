<template>
    <fgp-property
        name="cacheDirectory"
        type="java.io.File"
        required
        default-value='file("${projectDir}/.frontend-gradle-plugin")'
        :tasks="['resolvePackageManager', 'installPackageManager']"
    >
        <p>
            Directory where the plugin writes some task outputs as files. This is the only way to get Gradle perform
            up-to-date checks, and reuse outputs without re-executing the related task (see also
            <fgp-site-link path="/faqs#cache-directory">this FAQ</fgp-site-link>).
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
