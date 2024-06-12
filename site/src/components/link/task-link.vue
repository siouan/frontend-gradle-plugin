<template>
    <FgpSiteLink :path="`${$config.public.paths.tasks}#${name}`">
        <FgpCode>{{ name }}</FgpCode>
    </FgpSiteLink>
</template>

<script setup lang="ts">
interface Props {
    readonly name: string;
}

defineProps<Props>();
</script>
