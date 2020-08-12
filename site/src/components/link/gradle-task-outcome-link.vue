<template>
    <fgp-gradle-docs-link path="/current/userguide/more_about_tasks.html#sec:task_outcomes">{{
        outcome
    }}</fgp-gradle-docs-link>
</template>

<script>
import Vue from "vue";
import fgpGradleDocsLink from "./gradle-docs-link";

export default Vue.component("fgp-gradle-task-outcome-link", {
    components: { fgpGradleDocsLink },
    props: {
        outcome: {
            type: String,
            required: true
        }
    }
});
</script>
