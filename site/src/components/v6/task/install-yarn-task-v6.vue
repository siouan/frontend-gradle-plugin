<template>
    <FgpTask name="installYarn">
        <template v-slot:title>Install Yarn</template>
        <template v-slot:description>
            <p>
                If the <FgpPropertyLink name="yarnEnabled" /> property is <FgpCode>true</FgpCode>, the task
                executes command <FgpCode>yarn set version &lt;yarnVersion&gt;</FgpCode> in the project directory.
                In other words, the task installs the relevant Yarn distribution in the current project, based on the
                value of the <FgpPropertyLink name="yarnVersion" /> property. Downloading and installing the
                distribution is entirely managed by the <FgpYarnLink /> distribution globally installed after
                execution of the <FgpTaskLink name="installGlobalYarn" />. To customize this process (proxy usage,
                etc), please refer to Yarn's
                <FgpLink href="https://yarnpkg.com/configuration/yarnrc">configuration options</FgpLink>.
            </p>
            <p>
                If a <FgpYarnLink /> distribution is already installed in the local platform - either as a global
                installation or as an installation performed by another Gradle (sub-)project - and shall be used instead
                of a downloaded distribution, take a look at the
                <FgpPropertyLink name="yarnDistributionProvided" /> property instead: when <FgpCode>true</FgpCode>,
                this task is ignored if invoked during a Gradle build, and its outcome will always be
                <FgpGradleTaskOutcomeLink outcome="SKIPPED" />.
            </p>
            <p>
                The task takes advantage of
                <FgpGradleGuidesLink path="/performance/#incremental_build"
                >Gradle incremental build</FgpGradleGuidesLink>, and is not executed again unless one of its
                inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be
                <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" />.
            </p>
            <FgpInfo>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </FgpInfo>
        </template>
    </FgpTask>
</template>
