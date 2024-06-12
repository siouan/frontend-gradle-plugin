<template>
    <FgpTask name="publishFrontend">
        <template #title>Publish frontend artifacts</template>
        <template #description>
            <p>
                This task may be used to integrate a publish script into a Gradle build. The publish script shall be
                defined in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="publishScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>yarn</FgpCode> command. This task depends on
                the <FgpTaskLink name="assembleFrontend" /> task, and is skipped either if the
                <FgpPropertyLink name="assembleScript" /> property is <FgpCode>null</FgpCode>, or if the
                <FgpPropertyLink name="publishScript" /> property is <FgpCode>null</FgpCode>. Apart from direct
                execution, the task is also executed when the Gradle <FgpCode>publish</FgpCode> task is executed.
            </p>
        </template>
    </FgpTask>
</template>
