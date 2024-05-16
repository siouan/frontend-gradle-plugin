<template>
    <article class="container-fluid border rounded my-4">
        <div class="row align-items-center bg-light">
            <div class="d-none d-md-inline-block col-sm-2 text-center">
                <i class="fa-4x mx-auto" :class="iconClass" />
            </div>
            <div class="col px-4 bg-white">
                <h5 class="mt-3">{{ title }}</h5>
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
