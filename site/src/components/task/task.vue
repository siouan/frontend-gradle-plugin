<template>
    <article class="mb-3 py-4 border-bottom">
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
                            <FgpTaskPropertyCommandLineOptionBadge v-if="input.commandLineOptionSupported" />
                            <FgpOptionalTaskPropertyBadge v-if="input.optionalHint" :title="input.optionalHint"
                            /> <FgpCode>{{ input.name }}</FgpCode>:
                            <template v-if="input.binding === 'P'">
                                <FgpPropertyLink :name="input.property" /> property
                            </template>
                            <slot v-else-if="input.binding === 'C'" :name="input.name" />
                            <template v-else-if="input.binding === 'U'">user-defined</template>
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
                <li v-if="customEnvironmentVariablesSupported">Supports <FgpSiteLink
                    :path="`${$config.public.paths.tasks}#custom-environment-variables`"
                >custom environment variables</FgpSiteLink></li>
                <li v-if="example">
                    Example: <FgpCode>{{ example }}</FgpCode>
                </li>
            </ul>
        </header>
        <section class="px-3"><slot name="description" /></section>
    </article>
</template>

<script setup lang="ts">
interface Input {
    readonly name: string;
    readonly type: TaskPropertyTypeType;
    readonly binding: TaskPropertyBindingType;
    readonly property?: string;
    readonly optionalHint?: string;
    readonly commandLineOptionSupported?: boolean;
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
    readonly customEnvironmentVariablesSupported?: boolean;
    readonly example?: string | null;
}

const slots = useSlots();
withDefaults(defineProps<Props>(), {
    type: false,
    dependingTaskNames: () => [],
    inputs: () => [],
    outputs: () => [],
    cacheable: false,
    customEnvironmentVariableSupported: false
});
const skippable = computed(() => !!slots.skipConditions);
</script>

<style scoped>
.fgp-cacheable-task {
    color: var(--bs-body-bg);
    background-color: var(--bs-emphasis-color);
}
</style>
