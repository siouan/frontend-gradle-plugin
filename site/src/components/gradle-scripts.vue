<template>
    <div class="mb-2">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link" :class="groovyTabClass" href="#" @click.prevent="setGroovyTabVisible">Groovy</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" :class="kotlinTabClass" href="#" @click.prevent="setKotlinTabVisible">Kotlin</a>
            </li>
        </ul>
        <div class="pt-3 pb-1 pl-3 bg-light">
            <div v-show="groovyTabVisible"><slot name="groovy" /></div>
            <div v-show="kotlinTabVisible"><slot name="kotlin" /></div>
        </div>
    </div>
</template>

<script>
import Vue from "vue";
import { mapGetters, mapMutations } from "vuex";

export default Vue.component("fgp-gradle-scripts", {
    computed: {
        groovyTabClass() {
            return {
                active: this.groovyTabVisible,
            };
        },
        kotlinTabClass() {
            return {
                active: this.kotlinTabVisible,
            };
        },
        ...mapGetters("gradle-scripts", ["groovyTabVisible", "kotlinTabVisible"]),
    },
    methods: {
        ...mapMutations("gradle-scripts", ["setGroovyTabVisible", "setKotlinTabVisible"])
    }
});
</script>
