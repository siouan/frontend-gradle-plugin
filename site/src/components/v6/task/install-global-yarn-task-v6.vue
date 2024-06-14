<template>
    <FgpTask name="installGlobalYarn">
        <template v-slot:title>
            Install <FgpYarnLink /> globally in the <FgpNodejsLink /> distribution
        </template>
        <template v-slot:description>
            <p>
                The task installs the latest <FgpYarnLink /> release as a global <FgpNodejsLink /> dependency. This
                is a step required to spawn per-project installations, as explained in the
                <FgpLink href="https://yarnpkg.com/getting-started/install#about-global-installs">installation notes</FgpLink>
                and
                <FgpLink href="https://yarnpkg.com/getting-started/migration#step-by-step">migration guide</FgpLink>
                of <FgpYarnLink />. The download and installation is done by executing command
                <FgpCode>npm install -g yarn</FgpCode>, using the <FgpCode>npm</FgpCode> executable located in the
                <FgpNodejsLink /> distribution deployed by the <fgp-task-link name="installNode" /> task, or in the
                provided distribution if the <FgpPropertyLink name="nodeDistributionProvided" /> property is set to
                <FgpCode>true</FgpCode>.
            </p>
            <FgpInfo>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </FgpInfo>
        </template>
    </FgpTask>
</template>
