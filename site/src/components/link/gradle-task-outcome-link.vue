<template>
    <FgpGradleDocsLink path="/current/userguide/more_about_tasks.html#sec:task_outcomes">{{
        outcome
    }}</FgpGradleDocsLink>
</template>

<script setup lang="ts">
interface Props {
    readonly outcome: string;
}

defineProps<Props>();
</script>
