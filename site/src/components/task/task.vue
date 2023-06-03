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
            <ul>
                <li v-if="inputs.length > 0">
                    Inputs:
                    <ul>
                        <li v-for="(input, index) in inputs" :key="index">
                            <fgp-task-property-type :type="input.type" /><fgp-code>{{ input.name }}</fgp-code>:
                            <template v-if="input.binding === 'P'">
                                <fgp-property-link :name="input.property" /> property
                            </template>
                            <slot v-if="input.binding === 'C'" :name="input.name" />
                        </li>
                    </ul>
                </li>
                <li v-if="outputs.length > 0">
                    Outputs:
                    <ul>
                        <li v-for="(output, index) in outputs" :key="index">
                            <fgp-task-property-type :type="output.type" /><fgp-code>{{ output.name }}</fgp-code>:
                            <slot v-if="output.binding === 'C'" :name="output.name" />
                        </li>
                    </ul>
                </li>
                <li v-if="skippable">
                    Skipped when <slot name="skipConditions" />
                </li>
            </ul>
        </header>
        <section class="px-3"><slot name="description" /></section>
    </article>
</template>

<script>
import Vue from 'vue';
import fgpTaskPropertyType from '@/components/task/task-property-type';
import fgpTaskLinkAnchor from '@/components/link/task-link-anchor';

export default Vue.component('fgp-task', {
    components: {
        fgpTaskPropertyType, fgpTaskLinkAnchor
    },
    props: {
        name: {
            type: String,
            required: true
        },
        type: {
            type: Boolean,
            default: false
        },
        inputs: {
            type: Array,
            default: () => []
        },
        outputs: {
            type: Array,
            default: () => []
        }
    },
    computed: {
        skippable() {
            return !!this.$slots.skipConditions;
        }
    }
});
</script>
