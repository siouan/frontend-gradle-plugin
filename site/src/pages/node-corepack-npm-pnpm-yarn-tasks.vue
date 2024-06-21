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
            <FgpDependencyTree class="w-100" />
        </p>

        <FgpSubTitle>Reference</FgpSubTitle>

        <section>
            <FgpSubSubTitle>Built-in tasks</FgpSubSubTitle>

            <FgpInstallNodeTask />
            <FgpInstallCorepackTask />
            <FgpResolvePackageManagerTask />
            <FgpInstallPackageManagerTask />
            <FgpInstallFrontendTask />
            <FgpCleanFrontendTask />
            <FgpAssembleFrontendTask />
            <FgpCheckFrontendTask />
            <FgpPublishFrontendTask />
        </section>

        <section>
            <FgpSubSubTitle>Additional types</FgpSubSubTitle>

            <FgpRunNodeTaskType />
            <FgpRunCorepackTaskType />
            <FgpRunNpmTaskType />
            <FgpRunPnpmTaskType />
            <FgpRunYarnTaskType />
        </section>

        <section>
            <FgpSubSubTitle id="custom-environment-variables">
                Environment variables in <FgpCode>node</FgpCode>-based tasks
                <FgpSiteLink :path="`${$config.public.paths.tasks}#app`" class="small text-info">&uparrow;</FgpSiteLink>
            </FgpSubSubTitle>

            <p>
                The plugin provides multiple tasks or types that execute under-the-hood a <FgpCode>node</FgpCode>-based
                command: <FgpTaskLink name="installCorepack" />, <FgpTaskLink name="installPackageManager" />,
                <FgpTaskLink name="installFrontend" />, <FgpTaskLink name="cleanFrontend" />,
                <FgpTaskLink name="assembleFrontend" />, <FgpTaskLink name="checkFrontend" />,
                <FgpTaskLink name="publishFrontend" />, <FgpTaskLink name="RunNode" />,
                <FgpTaskLink name="RunCorepack" />, <FgpTaskLink name="RunNpm" />, <FgpTaskLink name="RunPnpm" />,
                <FgpTaskLink name="RunYarn" />. These tasks forward environment variables visible by the Gradle process
                to <FgpCode>node</FgpCode>, <FgpCode>corepack</FgpCode>, <FgpCode>npm</FgpCode>,
                <FgpCode>pnpm</FgpCode>, <FgpCode>yarn</FgpCode> commands. These variables may be overwritten and/or new
                variables may be added to the environment forwarded to the command. If you need to alter the
                <FgpCode>PATH</FgpCode> environment variable, and though this is generally a rare situation, keep in
                mind the plugin adds its own paths so as the relevant <FgpCode>node</FgpCode> executable can be found.
            </p>
            <p>Example hereafter shows how to customize the environment for a given task:</p>
            <FgpGradleScripts>
                <template #groovy>
                    <pre><FgpCode>
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask
tasks.named('assembleFrontend', AssembleTask) {
    environmentVariables.put('NODE_OPTIONS', '--max_old_space_size=50 --title="Assembling frontend"')
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask
tasks.named&lt;AssembleTask&gt;("assembleFrontend") {
    environmentVariables.put("NODE_OPTIONS", "--max_old_space_size=50 --title=\"Assembling frontend\"")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
            <p>
                Example hereafter shows how to apply an environment variable to all tasks running a <FgpCode
                >node</FgpCode>-based command:
            </p>
            <FgpGradleScripts>
                <template #groovy>
                    <pre><FgpCode>
import org.siouan.frontendgradleplugin.infrastructure.gradle.AbstractRunCommandTask
tasks.withType(AbstractRunCommandTask) {
    environmentVariables.put('NODE_DEBUG', 'module')
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>
import org.siouan.frontendgradleplugin.infrastructure.gradle.AbstractRunCommandTask
tasks.withType&lt;AbstractRunCommandTask&gt; {
    environmentVariables.put("NODE_DEBUG", "module")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <FgpSubSubTitle id="builtin-tasks-customization">
                Built-in tasks customization
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
            <FgpGradleScripts>
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
tasks.named&lt;InstallFrontendTask&gt;("installFrontend") {
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
const title = 'Gradle tasks to run node, corepack, npm, pnpm, yarn commands';
const description = 'Gradle tasks and types provided by the plugin to run node/corepack/npm/pnpm/yarn: integration, dependencies, customization, recommendations';

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
