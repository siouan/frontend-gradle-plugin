<template>
    <section>
        <fgp-main-title>Tasks</fgp-main-title>

        <p>
            The plugin registers multiple tasks, that may have dependencies with each other, and also with:
        </p>
        <ul>
            <li>
                Gradle lifecycle tasks defined in the
                <fgp-gradle-docs-link path="/current/userguide/base_plugin.html"
                    >Gradle Base plugin</fgp-gradle-docs-link
                >: <fgp-code>clean</fgp-code>, <fgp-code>assemble</fgp-code>, <fgp-code>check</fgp-code>.
            </li>
            <li>Tasks defined in the Gradle Publishing plugin: <fgp-code>publish</fgp-code>.</li>
        </ul>

        <fgp-sub-title>Dependency tree</fgp-sub-title>

        <p class="text-center">
            <fgp-dependency-tree class="w-100" />
        </p>

        <fgp-sub-title>Reference</fgp-sub-title>

        <section>
            <fgp-sub-sub-title>Built-in tasks</fgp-sub-sub-title>

            <fgp-install-node-task />
            <fgp-resolve-package-manager-task />
            <fgp-install-package-manager-task />
            <fgp-install-frontend-task />
            <fgp-clean-frontend-task />
            <fgp-assemble-frontend-task />
            <fgp-check-frontend-task />
            <fgp-publish-frontend-task />
        </section>

        <section>
            <fgp-sub-sub-title>Additional types</fgp-sub-sub-title>

            <fgp-run-node-task-type />
            <fgp-run-corepack-task-type />
            <fgp-run-npm-task-type />
            <fgp-run-pnpm-task-type />
            <fgp-run-yarn-task-type />
        </section>

        <section>
            <fgp-sub-sub-title id="tweaking-tasks">
                Tweaking the built-in tasks
                <fgp-site-link path="#app" class="small text-info">&uparrow;</fgp-site-link>
            </fgp-sub-sub-title>

            <p>
                If you need to customize the plugin's built-in tasks (e.g. declare additional I/O or dependencies), it
                is important to conform to the
                <fgp-gradle-docs-link
                    path="/current/userguide/task_configuration_avoidance.html#sec:old_vs_new_configuration_api_overview"
                    >Configuration avoidance API</fgp-gradle-docs-link
                >: use references of task providers instead of references of tasks, and continue taking advantage of the
                lazy configuration strategy the plugin already implements. The examples below introduce the
                implementation expected with simple cases:
            </p>
            <fgp-gradle-scripts id="lazy-configuration-examples" class="mt-3">
                <template #groovy>
                    <pre><fgp-code><fgp-code-comment>// Configuring a predefined task.
// FORMER SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.</fgp-code-comment>
installFrontend {
    dependsOn 'otherTask'
    inputs.files('package.json', 'package-lock.json')
}
<fgp-code-comment>// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later. Let's also reinforce this behaviour
// by using its provider to retrieve the task.</fgp-code-comment>
tasks.named('installFrontend') {
    dependsOn tasks.named('otherTask')
    inputs.files('package.json', 'package-lock.json')
}

<fgp-code-comment>// Defining a new task
// LEGACY SYNTAX: task 'eagerTask' is immediately created and configured, as well as task
// 'installFrontend', even if both tasks are not executed.</fgp-code-comment>
task eagerTask {
    dependsOn 'installFrontend'
}
<fgp-code-comment>// MODERN SYNTAX: task 'lazyTask' is created and configured only when Gradle is about to execute it.
// Consequently, task 'installFrontend' is also created and configured later. Let's also reinforce this
// behaviour by using its provider to retrieve the task.</fgp-code-comment>
tasks.register('lazyTask') {
    dependsOn tasks.named('installFrontend')
}</fgp-code></pre>
                </template>
                <template #kotlin>
                    <pre><fgp-code><fgp-code-comment>// Configuring a predefined task.
// FORMER SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.</fgp-code-comment>
installFrontend {
    dependsOn("otherTask")
    inputs.files("package.json", "package-lock.json")
}
<fgp-code-comment>// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later.</fgp-code-comment>
tasks.named&lt;InstallTask&gt;("installFrontend") {
    dependsOn(tasks.named("otherTask"))
    inputs.files("package.json", "package-lock.json")
}

<fgp-code-comment>// Defining a new task
// LEGACY SYNTAX: task 'eagerTask' is immediately created and configured, as well as task
// 'installFrontend', even if both tasks are not executed.</fgp-code-comment>
task eagerTask {
    dependsOn("installFrontend")
}
<fgp-code-comment>// MODERN SYNTAX: task 'lazyTask' is created and configured only when Gradle is about to execute it.
// Consequently, task 'installFrontend' is also created and configured later. Let's also reinforce this
// behaviour by using its provider to retrieve the task.</fgp-code-comment>
tasks.register("lazyTask") {
    dependsOn(tasks.named("installFrontend"))
}</fgp-code></pre>
                </template>
            </fgp-gradle-scripts>
            <p>
                If your application uses the legacy syntax, you may find further instructions to migrate to the modern
                syntax in this Gradle
                <fgp-gradle-docs-link
                    path="/current/userguide/task_configuration_avoidance.html#sec:task_configuration_avoidance_migration_guidelines"
                    >guide</fgp-gradle-docs-link
                >.
            </p>
        </section>
    </section>
</template>

<script>
import Vue from 'vue';
import fgpAssembleFrontendTask from '@/components/task/assemble-frontend-task';
import fgpCheckFrontendTask from '@/components/task/check-frontend-task';
import fgpCleanFrontendTask from '@/components/task/clean-frontend-task';
import fgpCode from '@/components/code.vue';
import fgpCodeComment from '@/components/code-comment';
import fgpDependencyTree from '@/components/task/dependency-tree';
import fgpGradleScripts from '@/components/gradle-scripts';
import fgpInstallFrontendTask from '@/components/task/install-frontend-task';
import fgpInstallNodeTask from '@/components/task/install-node-task';
import fgpInstallPackageManagerTask from '@/components/task/install-package-manager-task';
import fgpMainTitle from '@/components/main-title';
import fgpPageMeta from '@/mixin/page-meta';
import fgpPublishFrontendTask from '@/components/task/publish-frontend-task';
import fgpResolvePackageManagerTask from '@/components/task/resolve-package-manager-task';
import fgpRunCorepackTaskType from '@/components/task/run-corepack-task-type';
import fgpRunNodeTaskType from '@/components/task/run-node-task-type';
import fgpRunNpmTaskType from '@/components/task/run-npm-task-type';
import fgpRunPnpmTaskType from '@/components/task/run-pnpm-task-type';
import fgpRunYarnTaskType from '@/components/task/run-yarn-task-type';
import fgpSubSubTitle from '@/components/sub-sub-title';
import fgpSubTitle from '@/components/sub-title';

export default Vue.component('fgp-node-corepack-npm-pnpm-yarn-tasks', {
    components: {
        fgpAssembleFrontendTask,
        fgpCheckFrontendTask,
        fgpCleanFrontendTask,
        fgpCode,
        fgpCodeComment,
        fgpDependencyTree,
        fgpGradleScripts,
        fgpInstallFrontendTask,
        fgpInstallNodeTask,
        fgpInstallPackageManagerTask,
        fgpMainTitle,
        fgpPublishFrontendTask,
        fgpResolvePackageManagerTask,
        fgpRunCorepackTaskType,
        fgpRunNodeTaskType,
        fgpRunNpmTaskType,
        fgpRunPnpmTaskType,
        fgpRunYarnTaskType,
        fgpSubSubTitle,
        fgpSubTitle
    },
    mixins: [fgpPageMeta],
    data() {
        return {
            htmlTitle: 'Gradle tasks to run node, npm, npx, yarn commands',
            metaDescription: 'Gradle tasks and types provided by the plugin to run node/npm/npx/yarn: integration, dependencies, customization, recommendations',
            linkCanonicalHref: process.env.FGP_WEBSITE_URL + 'node-corepack-npm-pnpm-yarn-tasks/'
        }
    }
});
</script>
