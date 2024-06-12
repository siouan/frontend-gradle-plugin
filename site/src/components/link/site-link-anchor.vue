<template>
    <FgpSiteLink :id="name" :path="pathWithAnchor" class="text-info">#</FgpSiteLink>
</template>

<script setup lang="ts">
interface Props {
    readonly name: string;
    readonly path: string;
}

const props = defineProps<Props>();

const pathWithAnchor = computed(() => `${props.path}#${props.name}`);
</script>
