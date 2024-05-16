<template>
    <div class="container">
        <FgpHeader @lang-change="changeLang($event)" />
        <main role="main">
            <slot />
        </main>
        <FgpFooter />
    </div>
</template>

<script setup lang="ts">
const switchLocalePath = useSwitchLocalePath();

function changeLang(lang: string): void {
    switchLocalePath(lang);
}
</script>
