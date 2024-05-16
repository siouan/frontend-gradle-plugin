<template>
    <FgpSiteLink :path="`${$config.public.paths.configuration}#${name}`">
        <template v-if="$slots.default"><slot /></template>
        <FgpCode v-else>{{ name }}</FgpCode>
    </FgpSiteLink>
</template>

<script setup lang="ts">
interface Props {
    readonly name: string;
}

defineProps<Props>();
</script>
