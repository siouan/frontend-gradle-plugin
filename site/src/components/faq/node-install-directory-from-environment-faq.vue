<template>
    <FgpFaq>
        <template #question>
            How can I set the install directory of the <FgpNodejsLink /> distribution using an environment variable?
        </template>
        <template #answer>
            <p>
                The best way to set the install directory
                <FgpGradleDocsLink path="/current/userguide/build_environment.html#sec:gradle_environment_variables"
                    >using the value of an environment variable</FgpGradleDocsLink
                >
                is to rely on Gradle API rather than on Java API:
            </p>

            <FgpGradleScripts id="plugin-dsl">
                <template #groovy>
                    <pre><FgpCode>frontend {
    <FgpPropertyLink name="nodeInstallDirectory" /> = providers.environmentVariable('NODEJS_HOME')
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>frontend {
    <FgpPropertyLink name="nodeInstallDirectory" />.set(providers.environmentVariable("NODEJS_HOME"))
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpFaq>
</template>
