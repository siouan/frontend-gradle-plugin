<template>
    <article class="mb-3 border-bottom">
        <header>
            <h4>
                <fgp-task-link-anchor :name="name" /> <template v-if="type">Type</template>
                <template v-else>Task</template>
                <fgp-code>
                    {{ name }}
                </fgp-code>
                -
                <slot name="title" /> <fgp-site-link path="#app" class="small text-info">&uparrow;</fgp-site-link>
            </h4>
        </header>
        <section class="px-3"><slot name="description" /></section>
    </article>
</template>

<script>
import Vue from "vue";
import fgpTaskLinkAnchor from "../link/task-link-anchor";

export default Vue.component("fgp-task", {
    components: {
        fgpTaskLinkAnchor
    },
    props: {
        name: {
            type: String,
            required: true
        },
        type: {
            type: Boolean,
            default: false
        }
    }
});
</script>
