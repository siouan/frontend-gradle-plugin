<template>
    <FgpTask name="installFrontend" :inputs="inputs">
        <template #title>Install frontend dependencies</template>
        <template #skipConditions>
            file <FgpPropertyLink name="cacheDirectory" /><FgpCode
            >/resolvePackageManager/package-manager-specification.txt</FgpCode> does not exist.
        </template>
        <template #description>
            <p>
                Depending on the package manager, this task executes either command
                <FgpCode>npm install</FgpCode>, or command <FgpCode>pnpm install</FgpCode>, or command
                <FgpCode>yarn install</FgpCode>, by default. Consequently, the command shall install project
                dependencies according to the algorithm followed by each package manager (see hereafter). Optionally,
                this command may be customized with the <FgpPropertyLink name="installScript" /> property (e.g. to run a
                <FgpCode>npm ci</FgpCode> command instead). On a developer workstation, executing this task is a good
                starting point to setup a workspace for development as it will install the
                <FgpNodejsLink /> distribution (if not provided) as well as dependencies.
            </p>

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">
                        About
                        <FgpGradleDocsLink path="/current/userguide/incremental_build.html"
                            >incremental build</FgpGradleDocsLink
                        >
                        and up-to-date checks
                    </h5>
                    <div class="card-text">
                        <p>
                            If you execute this task several times in a row, you may notice the
                            <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                            command is always executed: Gradle does not reuse task outputs based on a previous execution
                            with the
                            <FgpGradleTaskOutcomeLink outcome="SUCCESS" /> outcome. This is the expected behaviour
                            <span class="font-italic">by default</span> because the task does not declare any relevant
                            input(s) and output(s) Gradle could track to know the task is already
                            <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" /> (e.g. unlike the
                            <FgpTaskLink name="installNode" /> task). Resolving these inputs/outputs is a bit complex,
                            since it depends on the package manager, the value of the
                            <FgpPropertyLink name="installScript" /> property, and the files present in the project.
                            That's why incremental build for this task is not available out-of-the-box by now. However,
                            some
                            <FgpRepoLink path="/tree/main/examples">examples</FgpRepoLink>
                            provide guidelines to customize this task and limit executions under certain circumstances.
                            Notes hereafter provide also some unofficial ideas:
                        </p>
                        <ul>
                            <li>
                                <FgpNpmLink />: inputs may be one or more of files <FgpCode>package.json</FgpCode>,
                                <FgpCode>npm-shrinkwrap.json</FgpCode>, <FgpCode>package-lock.json</FgpCode>,
                                <FgpCode>yarn.lock</FgpCode>, while outputs may be the
                                <FgpCode>node_modules</FgpCode> directory and the
                                <FgpCode>package-lock.json</FgpCode> file (see
                                <FgpNpmDocsLink path="/cli/v9/commands/npm-install">npm install</FgpNpmDocsLink>). If
                                the <FgpPropertyLink name="installScript" /> property is set with <FgpCode>ci</FgpCode>,
                                files <FgpCode>npm-shrinkwrap.json</FgpCode> and
                                <FgpCode>package-lock.json</FgpCode> may be the only possible input file, if one or the
                                other exists, and the <FgpCode>node_modules</FgpCode> directory the only output.
                            </li>
                            <li>
                                <FgpPnpmLink />: inputs may be one or more of files <FgpCode>package.json</FgpCode>,
                                <FgpCode>pnpm-lock.yaml</FgpCode>, while outputs may be the
                                <FgpCode>node_modules</FgpCode> directory and the
                                <FgpCode>pnpm-lock.yaml</FgpCode> file.
                            </li>
                            <li>
                                <FgpYarnLink />: inputs may be one or more of files <FgpCode>package.json</FgpCode>,
                                <FgpCode>yarn.lock</FgpCode>, while outputs may be the
                                <FgpCode>node_modules</FgpCode> directory, or the <FgpCode>.pnp.cjs</FgpCode> file and
                                the <FgpCode>.yarn/cache</FgpCode> directory (<FgpYarnLink
                                    label="Zero-installs"
                                    title="Zero-Install feature"
                                />), and the <FgpCode>yarn.lock</FgpCode> file.
                            </li>
                        </ul>
                        <p>
                            If you are about to tweak this task to declare additional inputs and outputs, take a look at
                            these
                            <FgpSiteLink :path="`${$config.public.paths.tasks}#tweaking-tasks`">recommendations</FgpSiteLink>.
                        </p>
                    </div>
                </div>
            </div>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: 'F',
        binding: 'P',
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: 'P',
        property: 'nodeInstallDirectory',
    },
    { name: 'script', type: 'S', binding: 'P', property: 'installScript' },
];
</script>
