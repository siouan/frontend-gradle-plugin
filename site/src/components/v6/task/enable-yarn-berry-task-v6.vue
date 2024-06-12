<template>
    <FgpTask name="enableYarnBerry">
        <template v-slot:title>Enable Yarn Berry installation</template>
        <template v-slot:description>
            <p>
                The task enables Yarn Berry support as described in the
                <FgpLink href="https://yarnpkg.com/getting-started/install#per-project-install">installation notes</FgpLink>
                and
                <FgpLink href="https://yarnpkg.com/getting-started/migration#step-by-step">migration guide</FgpLink>
                of <FgpYarnLink />. This is done by executing command
                <FgpCode>yarn set version berry</FgpCode>, using the <FgpCode>yarn</FgpCode> executable globally
                installed by the <FgpTaskLink name="installGlobalYarn" /> task.
            </p>
            <FgpInfo>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </FgpInfo>
        </template>
    </FgpTask>
</template>
