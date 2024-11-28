<template>
    <article class="mb-3 py-4 border-bottom">
        <header>
            <h4>
                <FgpPropertyLinkAnchor :name="name" />
                Property
                <FgpCode> {{ name }} </FgpCode
                > <FgpSiteLink
                    :path="`${$config.public.paths.configuration}#app`"
                    class="small text-info"
                >&uparrow;</FgpSiteLink>
            </h4>
            <ul>
                <li v-if="taskNames.length > 0">
                    Related tasks:
                    <span v-for="(taskName, index) in taskNames" :key="taskName">
                        <FgpTaskLink :name="taskName" /><template v-if="index < taskNames.length - 1">, </template>
                    </span>
                </li>
                <li>
                    Type:
                    <FgpLink v-if="jdkHref" :href="jdkHref"><FgpCode>{{ type }}</FgpCode></FgpLink>
                    <FgpCode v-else>{{ type }}</FgpCode>
                </li>
                <li>
                    Required: <FgpCode>{{ required }}</FgpCode>
                </li>
                <li>
                    Default value:
                    <FgpCode v-if="!$slots.defaultValue">{{ defaultTypedValue }}</FgpCode
                    ><slot v-else name="defaultValue" />
                </li>
                <li v-if="example">
                    Example: <FgpCode>{{ scriptExample }}</FgpCode>
                </li>
            </ul>
        </header>
        <section class="px-3">
            <slot />
        </section>
    </article>
</template>

<script setup lang="ts">
const QUALIFIED_JDK_CLASS_NAME_REGEXP = /^(?<fqcn>javax?\.(?:[a-z]\w+\.)+[A-Z]\w+?)(?:<[\w.]+>)?$/;
const JDK_STRING_CLASS_NAME = 'java.lang.String';

interface Props {
    readonly name: string;
    readonly type: string;
    readonly required?: boolean;
    readonly defaultValue?: string | null;
    readonly example?: string | null;
    readonly taskNames: string[];
}

const props = withDefaults(defineProps<Props>(), {
    required: true,
    defaultValue: null,
    example: null,
});

const defaultTypedValue = computed(() => {
    if (props.defaultValue === null) {
        return 'null';
    }
    return props.type === JDK_STRING_CLASS_NAME ? `"${props.defaultValue}"` : props.defaultValue;
});
const jdkHref = computed(() => {
    if (props.type) {
        const matches = QUALIFIED_JDK_CLASS_NAME_REGEXP.exec(props.type);
        if (matches && matches.groups?.fqcn) {
            return `https://docs.oracle.com/en/java/javase/21/docs/api/java.base/${matches.groups.fqcn.replace(/\./g, '/')}.html`;
        }
    }
    return null;
});
const scriptExample = computed(() => (props.type === JDK_STRING_CLASS_NAME ? `"${props.example}"` : props.example));
</script>
