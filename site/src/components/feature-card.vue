<template>
    <article class="card mb-3">
        <div class="row g-0">
            <div class="col-sm-2 bg-body-secondary rounded-start text-center align-content-center d-none d-md-inline-block">
                <i class="fa-4x" :class="iconClass" />
            </div>
            <div class="col px-4 pt-3">
                <h5>{{ title }}</h5>
                <p>
                    <slot />
                </p>
            </div>
        </div>
    </article>
</template>

<script setup lang="ts">
interface Props {
    readonly title: string;
    readonly iconClass: string;
}

defineProps<Props>();
</script>
