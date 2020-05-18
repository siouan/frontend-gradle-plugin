<template>
    <div class="mb-2">
        <ul class="nav nav-tabs">
            <li class="nav-item" @click="setGroovyTabVisible()">
                <a class="nav-link" :class="groovyTabClass" href="#" @click.prevent>Groovy</a>
            </li>
            <li class="nav-item" @click="setKotlinTabVisible()">
                <a class="nav-link" :class="kotlinTabClass" href="#" @click.prevent>Kotlin</a>
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
import { STORE_MODULE_GRADLE_SCRIPTS } from "../../store";

export default Vue.component("fgp-gradle-scripts", {
    computed: {
        groovyTabClass: function() {
            return {
                active: this.groovyTabVisible
            };
        },
        kotlinTabClass: function() {
            return {
                active: this.kotlinTabVisible
            };
        },
        ...mapGetters(STORE_MODULE_GRADLE_SCRIPTS, ["groovyTabVisible", "kotlinTabVisible"])
    },
    methods: {
        ...mapMutations(STORE_MODULE_GRADLE_SCRIPTS, ["setGroovyTabVisible", "setKotlinTabVisible"])
    }
});
</script>
