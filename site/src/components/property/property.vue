<template>
    <article class="mb-3 border-bottom">
        <header>
            <h4>
                <fgp-property-link-anchor :name="name" />
                Property
                <fgp-code>
                    {{ name }}
                </fgp-code>
                <fgp-site-link path="#app" class="small text-info">&uparrow;</fgp-site-link>
            </h4>
            <ul>
                <li v-if="tasks.length > 0">
                    Related tasks:
                    <span v-for="(taskName, index) in tasks" :key="taskName">
                        <fgp-task-link :name="taskName" /><template v-if="index < tasks.length - 1">, </template>
                    </span>
                </li>
                <li>
                    Type:
                    <template v-if="jdkHref">
                        <fgp-link :href="jdkHref">
                            <fgp-code>{{ type }}</fgp-code>
                        </fgp-link>
                    </template>
                    <template v-else>
                        <fgp-code>{{ type }}</fgp-code>
                    </template>
                </li>
                <li>
                    Required: <fgp-code>{{ required }}</fgp-code>
                </li>
                <li>
                    Default value: <fgp-code>{{ defaultScriptValue }}</fgp-code>
                </li>
                <li v-if="example">
                    Example: <fgp-code>{{ scriptExample }}</fgp-code>
                </li>
            </ul>
        </header>
        <section class="px-3">
            <slot />
        </section>
    </article>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpLink from "../link/link";
import fgpPropertyLinkAnchor from "../link/property-link-anchor";
import fgpSiteLink from "../link/site-link";
import fgpTaskLink from "../link/task-link";

const QUALIFIED_JDK_CLASS_NAME_REGEXP = /^javax?\.([a-z]\w+\.)+[A-Z]\w+$/;
const JDK_STRING_CLASS_NAME = "java.lang.String";

export default Vue.component("fgp-property", {
    components: { fgpCode, fgpLink, fgpPropertyLinkAnchor, fgpSiteLink, fgpTaskLink },
    props: {
        name: {
            type: String,
            required: true,
        },
        type: {
            type: String,
            required: true
        },
        required: {
            type: Boolean,
            default: true,
        },
        defaultValue: {
            type: String,
            default: null,
        },
        example: {
            type: String,
            default: null,
        },
        tasks: {
            type: Array,
            required: true,
        },
    },
    computed: {
        defaultScriptValue() {
            if (this.defaultValue === null) {
                return "null";
            }
            return this.type === JDK_STRING_CLASS_NAME ? `"${this.defaultValue}"` : this.defaultValue;
        },
        jdkHref() {
            if (this.type && QUALIFIED_JDK_CLASS_NAME_REGEXP.test(this.type)) {
                return `https://docs.oracle.com/javase/8/docs/api/index.html?${this.type.replace(/\./, "/")}.html`;
            }
            return null;
        },
        scriptExample() {
            return this.type === JDK_STRING_CLASS_NAME ? `"${this.example}"` : this.example;
        },
    },
});
</script>
