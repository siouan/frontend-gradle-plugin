<template>
    <fgp-task name="installFrontend" :inputs="inputs">
        <template #title>Install frontend dependencies</template>
        <template #skipConditions>
            file <fgp-property-link name="cacheDirectory" /><fgp-code
        >/resolvePackageManager/package-manager-specification.txt</fgp-code> does not exist.
        </template>
        <template #description>
            <p>
                Depending on the package manager, this task executes either command <fgp-code>npm install</fgp-code>, or
                command <fgp-code>pnpm install</fgp-code>, or command <fgp-code>yarn install</fgp-code>, by default.
                Consequently, the command shall install project dependencies according to the algorithm followed by each
                package manager (see hereafter). Optionally, this command may be customized
                with the <fgp-property-link name="installScript" /> property (e.g. to run a <fgp-code>npm ci</fgp-code>
                command instead). On a developer workstation, executing this task is a good starting point to setup a
                workspace for development as it will install the <fgp-nodejs-link /> distribution (if not provided)
                as well as dependencies.
            </p>

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">About
                        <fgp-gradle-docs-link path="/current/userguide/incremental_build.html">incremental build</fgp-gradle-docs-link>
                        and up-to-date checks</h5>
                    <div class="card-text">
                        <p>
                        If you execute this task several times in a row, you may notice the
                        <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command is always
                        executed: Gradle does not reuse task outputs based on a previous execution with the
                        <fgp-gradle-task-outcome-link outcome="SUCCESS" /> outcome. This is the expected behaviour
                        <span class="font-italic">by default</span> because the task does not declare any relevant
                        input(s) and output(s) Gradle could track to know the task is already
                        <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" /> (e.g. unlike the
                        <fgp-task-link name="installNode" /> task). Resolving these inputs/outputs is a bit complex,
                        since it depends on the package manager, the value of the
                        <fgp-property-link name="installScript" /> property, and the files present in the project.
                        That's why incremental build for this task is not available out-of-the-box by now. However,
                        some <fgp-repo-link path="/tree/main/examples">examples</fgp-repo-link> provide guidelines
                        to customize this task and limit executions under certain circumstances. Notes hereafter provide
                        also some unofficial ideas:
                        </p>
                        <ul>
                            <li><fgp-npm-link />: inputs may be one or
                                more of files <fgp-code>package.json</fgp-code>,
                                <fgp-code>npm-shrinkwrap.json</fgp-code>, <fgp-code>package-lock.json</fgp-code>,
                                <fgp-code>yarn.lock</fgp-code>, while outputs may be the
                                <fgp-code>node_modules</fgp-code> directory and the
                                <fgp-code>package-lock.json</fgp-code> file (see
                                <fgp-npm-docs-link path="/cli/v9/commands/npm-install">npm install</fgp-npm-docs-link>).
                                If the <fgp-property-link name="installScript" /> property is set with
                                <fgp-code>ci</fgp-code>, files <fgp-code>npm-shrinkwrap.json</fgp-code> and
                                <fgp-code>package-lock.json</fgp-code> may be the only possible input file, if one or
                                the other exists, and the <fgp-code>node_modules</fgp-code> directory the only output.
                            </li>
                            <li><fgp-pnpm-link />: inputs may be one or
                                more of files <fgp-code>package.json</fgp-code>, <fgp-code>pnpm-lock.yaml</fgp-code>,
                                while outputs may be the <fgp-code>node_modules</fgp-code> directory and the
                                <fgp-code>pnpm-lock.yaml</fgp-code> file.
                            </li>
                            <li><fgp-yarn-link />: inputs may be one or
                                more of files <fgp-code>package.json</fgp-code>, <fgp-code>yarn.lock</fgp-code>,
                                while outputs may be the
                                <fgp-code>node_modules</fgp-code> directory, or the <fgp-code>.pnp.cjs</fgp-code> file
                                and the <fgp-code>.yarn/cache</fgp-code> directory
                                (<fgp-yarn-link label="Zero-installs" title="Zero-Install feature" />), and the
                                <fgp-code>yarn.lock</fgp-code> file.
                            </li>
                        </ul>
                        <p>
                        If you are about to tweak this task to declare additional inputs and outputs, take a look at
                        these <fgp-site-link path="#tweaking-tasks">recommendations</fgp-site-link>.
                        </p>
                    </div>
                </div>
            </div>
        </template>
    </fgp-task>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpGradleTaskOutcomeLink from '@/components/link/gradle-task-outcome-link';
import fgpNpmDocsLink from '@/components/link/npm-docs-link';
import fgpNpmLink from '@/components/link/npm-link';
import fgpPropertyLink from '@/components/link/property-link';
import fgpTask from '@/components/task/task';
import fgpTaskLink from '@/components/link/task-link';
import fgpYarnLink from '@/components/link/yarn-link';

export default Vue.component('fgp-install-frontend-task', {
    components: {
        fgpCode,
        fgpGradleTaskOutcomeLink,
        fgpNpmDocsLink,
        fgpNpmLink,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink,
        fgpYarnLink
    },
    data() {
        return {
            inputs: [
                { name: 'packageJsonDirectory', type: 'F', binding: 'P', property: 'packageJsonDirectory' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' },
                { name: 'script', type: 'S', binding: 'P', property: 'installScript' }
            ]
        };
    }
});
</script>
