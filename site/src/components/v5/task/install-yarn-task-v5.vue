<template>
    <FgpTask name="installYarn">
        <template v-slot:title>Install <FgpYarnLink :version="1" /></template>
        <template v-slot:description>
            <p>
                If the <FgpPropertyLink name="yarnEnabled" /> property is <FgpCode>true</FgpCode>, the task
                downloads and install a <FgpYarnLink :version="1" /> distribution in the directory pointed by the
                <FgpPropertyLink name="yarnInstallDirectory" />. The URL used to download the distribution is resolved
                using the <FgpPropertyLink name="yarnDistributionUrlRoot" /> property and the
                <FgpPropertyLink name="yarnDistributionUrlPathPattern" /> property. By default, the plugin relies on
                the VM <FgpJavaNetworkPropertiesLink>network properties</FgpJavaNetworkPropertiesLink> to know
                if a proxy server shall be used when downloading the distribution. A custom proxy server may also be
                used by defining the <FgpPropertyLink name="httpsProxyHost" /> property (respectively the
                <FgpPropertyLink name="httpProxyHost" /> property) if the
                <FgpPropertyLink name="yarnDistributionUrlRoot" /> property uses the <FgpCode>https</FgpCode>
                protocol (resp. uses the <FgpCode>http</FgpCode> protocol).
            </p>
            <p>
                If a <FgpYarnLink :version="1" /> distribution is already installed in the local platform - either as a global
                installation or as an installation performed by another Gradle (sub-)project - and shall be used instead
                of a downloaded distribution, take a look at the
                <FgpPropertyLink name="yarnDistributionProvided" /> property instead: when <FgpCode>true</FgpCode>,
                this task is ignored if invoked during a Gradle build, and its outcome will always be
                <FgpGradleTaskOutcomeLink outcome="SKIPPED" />.
            </p>
            <p>
                The task takes advantage of
                <FgpGradleGuidesLink path="/performance/#incremental_build"
                    >Gradle incremental build</FgpGradleGuidesLink
                >, and is not executed again unless one of its inputs/outputs changed. Consequently, if the task takes
                part of a Gradle build, its outcome will be <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" />.
            </p>
            <FgpInfo>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </FgpInfo>
        </template>
    </FgpTask>
</template>
