<template>
    <FgpTask name="checkFrontend">
        <template #title>Check frontend application</template>
        <template #description>
            <p>
                This task may be used to integrate a check script into a Gradle build. The check script shall be defined
                in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="checkScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>yarn</FgpCode>
                command. A typical check script may lint source files, execute tests, and/or perform additional analysis
                actions. This task depends on the
                <FgpTaskLink name="installFrontend" /> task, and is skipped if the
                <FgpPropertyLink name="checkScript" /> property is <FgpCode>null</FgpCode>. Apart from direct execution,
                the task is also executed when the Gradle lifecycle
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">check</FgpGradleDocsLink>
                task is executed.
            </p>
        </template>
    </FgpTask>
</template>
