<template>
    <div class="mb-3">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <NuxtLink
                    :external="true"
                    to="#"
                    class="nav-link"
                    :class="groovyTabClass"
                    @click.prevent="setGroovyTabVisible()"
                    >Groovy</NuxtLink
                >
            </li>
            <li class="nav-item">
                <NuxtLink
                    :external="true"
                    to="#"
                    class="nav-link"
                    :class="kotlinTabClass"
                    @click.prevent="setKotlinTabVisible()"
                    >Kotlin</NuxtLink
                >
            </li>
        </ul>
        <div class="px-3 pt-3 border border-top-0 rounded-bottom shadow-sm bg-light-subtle">
            <div v-show="groovyTabVisible"><slot name="groovy" /></div>
            <div v-show="kotlinTabVisible"><slot name="kotlin" /></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const mainStore = useMainStore();

const groovyTabVisible = computed(() => mainStore.preferedScriptLanguageId === ScriptLanguageId.GROOVY);
const kotlinTabVisible = computed(() => mainStore.preferedScriptLanguageId === ScriptLanguageId.KOTLIN);
const groovyTabClass = reactive({
    active: groovyTabVisible,
    'bg-light-subtle': groovyTabVisible
});
const kotlinTabClass = reactive({
    active: kotlinTabVisible,
    'bg-light-subtle': kotlinTabVisible
});

function setGroovyTabVisible(): void {
    mainStore.setPreferedScriptLanguageId(ScriptLanguageId.GROOVY);
}
function setKotlinTabVisible(): void {
    mainStore.setPreferedScriptLanguageId(ScriptLanguageId.KOTLIN);
}
</script>
