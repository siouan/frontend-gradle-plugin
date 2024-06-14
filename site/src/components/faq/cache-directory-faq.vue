<template>
    <FgpFaq>
        <template #question>
            What's the purpose of the
            <FgpCode>.frontend-gradle-plugin</FgpCode> directory generated in each project?
        </template>
        <template #answer>
            The plugin uses this directory to store some task outputs as files, so as Gradle can perform
            <FgpGradleDocsLink path="/current/userguide/incremental_build.html">up-to-date checks</FgpGradleDocsLink>.
            It allows to skip some task execution and optimize build time when inputs did not change, by reusing the
            already computed outputs. Generally, the <FgpCode>build</FgpCode> directory of the project is used for such
            goal, but the plugin cannot rely on this directory because it is removed when Gradle
            <FgpCode>clean</FgpCode> task is executed. This would remove mandatory outputs for plugin task
            <FgpTaskLink name="cleanFrontend" />.
        </template>
    </FgpFaq>
</template>
