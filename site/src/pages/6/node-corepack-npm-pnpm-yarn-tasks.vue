<template>
    <section>
        <FgpMainTitle>Tasks</FgpMainTitle>

        <p>The plugin registers multiple tasks, that may have dependencies with each other, and also with:</p>
        <ul>
            <li>
                Gradle lifecycle tasks defined in the
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html">Gradle Base plugin</FgpGradleDocsLink>:
                <FgpCode>clean</FgpCode>, <FgpCode>assemble</FgpCode>, <FgpCode>check</FgpCode>.
            </li>
            <li>
                Tasks defined in the Gradle Publishing plugin:
                <FgpCode>publish</FgpCode>.
            </li>
        </ul>

        <FgpSubTitle>Dependency tree</FgpSubTitle>

        <p class="text-center">
            <FgpDependencyTreeV6 class="w-75" />
        </p>

        <FgpSubTitle>Reference</FgpSubTitle>

        <section>
            <FgpSubSubTitle>Built-in tasks</FgpSubSubTitle>

            <FgpInstallNodeTaskV6 />
            <FgpInstallGlobalYarnTaskV6 />
            <fgpEnableYarnBerryTaskV6 />
            <FgpInstallYarnTaskV6 />
            <FgpInstallFrontendTaskV6 />
            <FgpCleanFrontendTaskV6 />
            <FgpAssembleFrontendTaskV6 />
            <FgpCheckFrontendTaskV6 />
            <FgpPublishFrontendTaskV6 />
        </section>

        <section>
            <FgpSubSubTitle>Additional types</FgpSubSubTitle>

            <FgpRunNodeTaskTypeV6 />
            <FgpRunNpmTaskTypeV6 />
            <FgpRunNpxTaskTypeV6 />
            <FgpRunYarnTaskTypeV6 />
        </section>

        <section>
            <FgpSubSubTitle id="tweaking-tasks">
                Tweaking the built-in tasks
                <FgpSiteLink :path="`${$config.public.paths.tasks}#app`" class="small text-info">&uparrow;</FgpSiteLink>
            </FgpSubSubTitle>

            <p>
                If you need to customize the plugin's built-in tasks (e.g. declare additional I/O or dependencies), it
                is important to conform to the
                <FgpGradleDocsLink
                    path="/current/userguide/task_configuration_avoidance.html#sec:old_vs_new_configuration_api_overview"
                    >Configuration avoidance API</FgpGradleDocsLink
                >: use references of task providers instead of references of tasks, and continue taking advantage of the
                lazy configuration strategy the plugin already implements. The examples below introduce the
                implementation expected with simple cases:
            </p>
            <FgpGradleScripts id="lazy-configuration-examples">
                <template #groovy>
                    <pre><FgpCode><FgpCodeComment>// Configuring a predefined task.
// LEGACY SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.</FgpCodeComment>
installFrontend {
    dependsOn 'otherTask'
    inputs.files('package.json', 'package-lock.json')
}
<FgpCodeComment>// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later. Let's also reinforce this behaviour
// by using its provider to retrieve the task.</FgpCodeComment>
tasks.named('installFrontend') {
    dependsOn tasks.named('otherTask')
    inputs.files('package.json', 'package-lock.json')
}

<FgpCodeComment>// Defining a new task
// LEGACY SYNTAX: task 'eagerTask' is immediately created and configured, as well as task
// 'installFrontend', even if both tasks are not executed.</FgpCodeComment>
task eagerTask {
    dependsOn 'installFrontend'
}
<FgpCodeComment>// MODERN SYNTAX: task 'lazyTask' is created and configured only when Gradle is about to execute it.
// Consequently, task 'installFrontend' is also created and configured later. Let's also reinforce this
// behaviour by using its provider to retrieve the task.</FgpCodeComment>
tasks.register('lazyTask') {
    dependsOn tasks.named('installFrontend')
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode><FgpCodeComment>// Configuring a predefined task.
// LEGACY SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.</FgpCodeComment>
installFrontend {
    dependsOn("otherTask")
    inputs.files("package.json", "package-lock.json")
}
<FgpCodeComment>// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later.</FgpCodeComment>
tasks.named&lt;InstallTask&gt;("installFrontend") {
    dependsOn(tasks.named("otherTask"))
    inputs.files("package.json", "package-lock.json")
}

<FgpCodeComment>// Defining a new task
// LEGACY SYNTAX: task 'eagerTask' is immediately created and configured, as well as task
// 'installFrontend', even if both tasks are not executed.</FgpCodeComment>
task eagerTask {
    dependsOn("installFrontend")
}
<FgpCodeComment>// MODERN SYNTAX: task 'lazyTask' is created and configured only when Gradle is about to execute it.
// Consequently, task 'installFrontend' is also created and configured later. Let's also reinforce this
// behaviour by using its provider to retrieve the task.</FgpCodeComment>
tasks.register("lazyTask") {
    dependsOn(tasks.named("installFrontend"))
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
            <p>
                If your application uses the legacy syntax, you may find further instructions to migrate to the modern
                syntax in this Gradle
                <FgpGradleDocsLink
                    path="/current/userguide/task_configuration_avoidance.html#sec:task_configuration_avoidance_migration_guidelines"
                    >guide</FgpGradleDocsLink
                >.
            </p>
        </section>
    </section>
</template>

<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const canonicalUrl = `${runtimeConfig.public.canonicalBaseUrl}${runtimeConfig.public.paths.tasks}`;
const title = 'Gradle tasks to run node, npm, npx, yarn commands';
const description = 'Gradle tasks and types provided by the plugin to run node/npm/npx/yarn: integration, dependencies, customization, recommendations';

useHead({
    link: [{ rel: 'canonical', href: canonicalUrl }]
});
useSeoMeta({
    description,
    ogDescription: description,
    ogTitle: title,
    ogUrl: canonicalUrl,
    title
});
</script>
