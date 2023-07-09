<template>
    <fgp-faq>
        <template #question>
            What's the purpose of the <fgp-code>.frontend-gradle-plugin</fgp-code> directory generated in each project?
        </template>
        <template #answer>
            The plugin uses this directory to store some task outputs as files, so as Gradle can perform
            <fgp-gradle-docs-link path="/current/userguide/incremental_build.html">up-to-date
            checks</fgp-gradle-docs-link>. It allows to skip some task execution and optimize build time when inputs did
            not change, by reusing the already computed outputs. In practice, this directory is generally located in the
            <fgp-code>build</fgp-code> directory of the project. However, the plugin cannot rely on this directory since
            it is removed when Gradle <fgp-code>clean</fgp-code> task is executed, which would remove mandatory outputs
            for plugin task <fgp-task-link name="cleanFrontend" />.
        </template>
    </fgp-faq>
</template>

<script>
import Vue from 'vue';
import fgpFaq from '@/components/faq/faq';

export default Vue.component('fgp-cache-directory-faq', {
    components: {
        fgpFaq
    }
});
</script>
