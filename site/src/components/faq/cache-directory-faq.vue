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
            already computed outputs.
        </template>
    </FgpFaq>
</template>
