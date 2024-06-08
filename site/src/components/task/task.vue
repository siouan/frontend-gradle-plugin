<template>
    <article class="mb-3 border-bottom">
        <header>
            <h4>
                <FgpTaskLink-anchor :name="name" /> <template v-if="type">Type</template> <template v-else
                >Task</template> <FgpCode>{{ name }}</FgpCode
                > - <slot name="title" /> <FgpGradleDocsLink
                    v-if="cacheable"
                    path="/current/userguide/build_cache.html#sec:task_output_caching_details"
                    title="Cacheable task"
                    class="badge fgp-cacheable-task"
                    hover-style-disabled
                    >C</FgpGradleDocsLink
                > <FgpSiteLink :path="`${$config.public.paths.tasks}#app`" class="small text-info">&uparrow;</FgpSiteLink>
            </h4>
            <ul>
                <li v-if="dependingTaskNames.length > 0">
                    Depends on:
                    <span v-for="(taskName, index) in dependingTaskNames" :key="taskName">
                        <FgpTaskLink :name="taskName" /><template v-if="index < dependingTaskNames.length - 1"
                            >,
                        </template>
                    </span>
                </li>
                <li v-if="inputs.length > 0">
                    Inputs:
                    <ul>
                        <li v-for="(input, index) in inputs" :key="index">
                            <FgpTaskPropertyType :type="input.type" />
                            <FgpOptionalTaskPropertyBadge v-if="input.optionalHint" :title="input.optionalHint"
                            /> <FgpCode>{{ input.name }}</FgpCode>:
                            <template v-if="input.binding === 'P'">
                                <FgpPropertyLink :name="input.property" /> property
                            </template>
                            <slot v-if="input.binding === 'C'" :name="input.name" />
                        </li>
                    </ul>
                </li>
                <li v-if="outputs.length > 0">
                    Outputs:
                    <ul>
                        <li v-for="(output, index) in outputs" :key="index">
                            <FgpTaskPropertyType :type="output.type" /> <FgpCode>{{ output.name }}</FgpCode
                            >:
                            <slot v-if="output.binding === 'C'" :name="output.name" />
                        </li>
                    </ul>
                </li>
                <li v-if="skippable">Skipped when <slot name="skipConditions" /></li>
            </ul>
        </header>
        <section class="px-3"><slot name="description" /></section>
        <footer v-if="outcomeHints.length > 0">
            <h6>Possible outcomes:</h6>
            <ul>
                <li v-for="outcomeHint in outcomeHints">
                    <FgpGradleTaskOutcomeLink :outcome="outcomeHint.outcome" />: {{ outcomeHint.description }}
                </li>
            </ul>
        </footer>
    </article>
</template>

<script setup lang="ts">
interface Input {
    readonly name: string;
    readonly type: TaskPropertyTypeType;
    readonly binding: TaskPropertyBindingType;
    readonly property?: string;
    readonly optionalHint?: string;
}

interface Output {
    readonly name: string;
    readonly type: TaskPropertyTypeType;
    readonly binding: TaskPropertyBindingType;
}

interface OutcomeHint {
    readonly outcome: TaskOutcomeType;
    readonly description: string;
}

interface Props {
    readonly name: string;
    readonly type?: boolean;
    readonly dependingTaskNames?: string[];
    readonly inputs?: any[];
    readonly outputs?: any[];
    readonly cacheable?: boolean;
    readonly outcomeHints?: OutcomeHint[];
}

const slots = useSlots();
withDefaults(defineProps<Props>(), {
    type: false,
    dependingTaskNames: () => [],
    inputs: () => [],
    outputs: () => [],
    cacheable: false,
    outcomeHints: () => []
});
const skippable = computed(() => !!slots.skipConditions);
</script>

<style scoped>
.fgp-cacheable-task {
    color: var(--bs-body-bg);
    background-color: var(--bs-emphasis-color);
}
</style>
