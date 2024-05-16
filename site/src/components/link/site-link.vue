<template>
    <NuxtLink :to="path" @click="propagateEvent($event)"><slot /></NuxtLink>
</template>

<script setup lang="ts">
interface Emits {
    (e: 'click', value: MouseEvent): void
}

interface Props {
    readonly path: string;
}

const emit = defineEmits<Emits>();
defineProps<Props>();

function propagateEvent(event: MouseEvent): void {
    emit('click', event);
}
</script>
