<template>
    <div class="mb-3">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <NuxtLink
                    :external="true"
                    to="#"
                    class="nav-link"
                    :class="groovyTabClass"
                    @click.prevent="mainStore.setGroovyTabVisible()"
                    >Groovy</NuxtLink
                >
            </li>
            <li class="nav-item">
                <NuxtLink
                    :external="true"
                    to="#"
                    class="nav-link"
                    :class="kotlinTabClass"
                    @click.prevent="mainStore.setKotlinTabVisible()"
                    >Kotlin</NuxtLink
                >
            </li>
        </ul>
        <div class="px-3 pt-3 border border-top-0 rounded-bottom shadow-sm bg-light">
            <div v-show="mainStore.groovyTabVisible"><slot name="groovy" /></div>
            <div v-show="mainStore.kotlinTabVisible"><slot name="kotlin" /></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const mainStore = useMainStore();

const groovyTabClass = computed(() => ({
    active: mainStore.groovyTabVisible,
}));
const kotlinTabClass = computed(() => ({
    active: mainStore.kotlinTabVisible,
}));
</script>
